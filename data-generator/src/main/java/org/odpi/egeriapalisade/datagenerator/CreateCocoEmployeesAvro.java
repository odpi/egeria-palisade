/* SPDX-License-Identifier: Apache-2.0 */
package org.odpi.egeriapalisade.datagenerator;

import com.github.javafaker.Faker;

import uk.gov.gchq.palisade.UserId;
import uk.gov.gchq.palisade.data.serialise.AvroSerialiser;
import uk.gov.gchq.palisade.example.hrdatagenerator.types.*;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class generates an avro file containing Egerias example company: Coco Pharmaceuticals personas https://github.com/odpi/data-governance/blob/master/docs/coco-pharmaceuticals/personas/README.md.
 * The avro format is based on employee schema in Palisade. Palisade has a data generator example that generates avro files using the Employee schema https://github.com/gchq/Palisade/tree/develop/example/hr-data-generator.
 * <p>
 * Build Prereqs:
 * - build Palisade as per https://github.com/gchq/Palisade/tree/develop/example/hr-data-generator.
 * - on the same machine then maven clean install this module.
 * - clone https://github.com/odpi/egeria into a folder <egeria>
 * This class runs as a java application, that takes 2 parameters, the output folder and the folder you have clones Egeria into <egeria>.
 * <p>
 * There is an avro file that has already been generated in data-generator/sample-data. As changes are made in this repository, Egeria or Palisade then this file may need to be re generated.
 * <p>
 * Note this file relies on the shape of example Egeria csv files.
 */
public class CreateCocoEmployeesAvro {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateCocoEmployeesAvro.class);
    /**
     * Male last names so we can file in the Sex field.
     */
    static String[] maleLastNames = new String[]{
            "Now",
            "Starter",
            "Padlock",
            "Nitter",
            "Stage",
            "Hopeful",
            "Geeke",
            "Profile",
            "Tally",
            "Keeper"
    };

    static Map<String, String> codeToDepNameMap = new HashMap<>();
    static Map<String, String> depNametoManagerMap = new HashMap();
    static Map<String, String> nametoBirthMap = new HashMap();
    static Map<String, Address> workLocNameToAddressMap = new HashMap();
    static String salaryFilePath;
    static String workLocationFilePath;
    static String outputFolderPath;
    static Random random = new Random(0);
    static Faker faker = ThreadLocalFaker.getFaker(random);

    /**
     * Some hard coded maps containing information about the coco personas
     */
    static void initializeMaps(int currentYear) {
        // department code to name
        codeToDepNameMap.put("4051", "Clinical Trials");
        codeToDepNameMap.put("3082", "IT");
        codeToDepNameMap.put("7432", "Chief Data Office");
        codeToDepNameMap.put("6877", "Accounts");
        codeToDepNameMap.put("6788", "Finance");
        codeToDepNameMap.put("2373", "Amsterdam Lab");
        codeToDepNameMap.put("5656", "London Lab");
        codeToDepNameMap.put("2343", "New York Lab");
        codeToDepNameMap.put("9999", "Founders");
        // map department name to manager id
        depNametoManagerMap.put("Clinical Trials", "302145");
        depNametoManagerMap.put("IT", "338575");
        depNametoManagerMap.put("Chief Data Office", "296776");
        depNametoManagerMap.put("Accounts", "896522");
        depNametoManagerMap.put("Finance", "188888");
        depNametoManagerMap.put("Amsterdam Lab", "439222");
        depNametoManagerMap.put("London Lab", "371803");
        depNametoManagerMap.put("New York Lab", "133777");
        // map last name to the date of birth - date of birth calculated from current year - age (from the personas web page)
        nametoBirthMap.put("Now", "10/11/" + (currentYear-59));
        nametoBirthMap.put("Starter", "10/11/" + (currentYear-59));
        nametoBirthMap.put("Daring", "10/11/" + (currentYear-59));
        nametoBirthMap.put("Tidy", "10/11/" + (currentYear-30));
        nametoBirthMap.put("Tasker", "10/11/" + (currentYear-34));
        nametoBirthMap.put("Tube", "10/11/" + (currentYear-35));
        nametoBirthMap.put("Quartile",  "10/11/" + (currentYear-37));
        nametoBirthMap.put("Padlock", "10/11/" + (currentYear-51));
        nametoBirthMap.put("Nitter",  "10/11/" + (currentYear-23));
        nametoBirthMap.put("Broker", "10/11/" + (currentYear-33));
        nametoBirthMap.put("Counter", "10/11/" + (currentYear-55));
        nametoBirthMap.put("Stage",  "10/11/" + (currentYear-36));
        nametoBirthMap.put("Overview",  "10/11/" + (currentYear-40));
        nametoBirthMap.put("Hopeful",  "10/11/" + (currentYear-26));
        nametoBirthMap.put("Geeke",  "10/11/" + (currentYear-23));
        nametoBirthMap.put("Mint",  "10/11/" + (currentYear-55));
        nametoBirthMap.put("Profile",  "10/11/" + (currentYear-27));
        nametoBirthMap.put("Tally", "10/11/" + (currentYear-24));
        nametoBirthMap.put("Keeper", "10/11/" + (currentYear-50));
    }


    public static void main(final String[] args) throws IOException {
        if (areArgsValid(args)) {
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);

            initializeMaps(currentYear);
            initializeworkLocation();
            File inputFile = new File(salaryFilePath);
            String outputFileName = outputFolderPath + "Employee.avro";
            long startTime = System.currentTimeMillis();
            AvroSerialiser<Employee> employeeAvroSerialiser = new AvroSerialiser<>(Employee.class);
            Stream<Employee> fullStream = null;
            try (OutputStream out = new FileOutputStream(outputFileName)) {
                BufferedReader br = new BufferedReader(new FileReader(inputFile));

                // get first line the headings and ignore
                String inputLine = br.readLine();

                while ((inputLine = br.readLine()) != null) {
                    //  //  System.out.println(inputLine);

                    Employee employee = new Employee();

                    //  populate file from csv file.
                    StringTokenizer inputTokenizer = new StringTokenizer(inputLine, ";");

                    // name
                    String firstName = "";
                    String lastName = "";
                    // address
                    String streetAddressNumber = "";
                    String streetName = "";
                    String city = "";
                    String state = "";

                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token HDR :" + tok);
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token RECTYPE :" + tok);
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token SERVICE :" + tok);
                        int hireYear = currentYear - new Integer(tok);
                        employee.setHireDate("06/01/" + hireYear);
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token EMPSTATUS :" + tok);
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token FNAME :" + tok);
                        firstName = tok;
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token LNAME :" + tok);
                        lastName = tok;
                        //set sex
                        Set<String> set = new HashSet<>();
                        Collections.addAll(set, maleLastNames);
                        if (set.contains(tok)) {
                            employee.setSex(Sex.Male);
                        } else {
                            employee.setSex(Sex.Female);
                        }
                        // set birth
                        employee.setDateOfBirth(nametoBirthMap.get(tok));
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token PNUM :" + tok);
                        UserId uid = new UserId();
                        uid.setId((firstName + lastName).toLowerCase());
                        employee.setUid(uid);
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token DEPT:" + tok);
                        // set Department name
                        String departmentName = codeToDepNameMap.get(tok);
                        String underScoredDepartmentName = departmentName.replace(' ', '_');
                        employee.setDepartment(Department.valueOf(underScoredDepartmentName));
                        //  set manager
                        String managerUid = depNametoManagerMap.get(departmentName);
                        if (managerUid != null) {
                            Manager manager = new Manager();
                            UserId uid = new UserId();
                            uid.setId(managerUid);
                            manager.setUid(uid);
                            manager.setManagerType("Line Manager");
                            Manager[] managers = new Manager[1];
                            managers[0] = manager;
                            employee.setManager(managers);
                        }
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token LVL:" + tok);
                        int i = 1;
                        for (Grade grade : Grade.values()) {
                            if (i == new Integer(tok)) {
                                employee.setGrade(grade);
                            }
                            i++;
                        }
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token LOCATION:" + tok);

                        String underScoredDepartmentName = tok.replace(' ', '_');
                        Address address = workLocNameToAddressMap.get(tok);
                        WorkLocation workLocation = new WorkLocation();
                        for (WorkLocationName wlName : WorkLocationName.values()) {
                            if (wlName.equals(underScoredDepartmentName)) {
                                workLocation.setWorkLocationName(wlName);
                            }
                        }
                        workLocation.setAddress(address);
                        employee.setWorkLocation(workLocation);
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token LOCCODE:" + tok);
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token HOL:" + tok);
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token ROLE:" + tok);
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token ETYPE:" + tok);
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token SALARY:" + tok);
                        employee.setSalaryAmount(new Integer(tok));
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token BONUS:" + tok);
                        employee.setSalaryBonus(new Integer(tok));
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token SNUM:" + tok);
                        streetAddressNumber = tok;
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token STREET:" + tok);
                        streetName = tok;
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token CITY:" + tok);
                        city = tok;
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token STATE:" + tok);
                        state = tok;
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token TAX:" + tok);
                    }
                    if (inputTokenizer.hasMoreTokens()) {
                        String tok = inputTokenizer.nextToken();
                        //  System.out.println("Token TAXP:" + tok);
                    }

                    employee.setName(firstName + " " + lastName);
                    Address address = Address.generate(faker, random);
                    address.setStreetAddressNumber(streetAddressNumber);
                    address.setCity(city);
                    address.setStreetName(streetName);
                    address.setState(state);
                    employee.setAddress(address);

                    // set Nationality randomly
                    Nationality nationality = Nationality.generate(new Random());
                    employee.setNationality(nationality);
                    PhoneNumber[] contacts = new PhoneNumber[2];
                    // set work phone number
                    PhoneNumber workPhoneNumber = PhoneNumber.generate(new Random());
                    workPhoneNumber.setType("Work");
                    contacts[0]=workPhoneNumber;

                    // set home phone number
                    PhoneNumber homePhoneNumber = PhoneNumber.generate(new Random());
                    homePhoneNumber.setType("Home");
                    contacts[1]=homePhoneNumber;
                    employee.setContactNumbers(contacts);

                    Stream<Employee> employeeStream = Stream.of(employee);
                    if (fullStream == null) {
                        fullStream = employeeStream;
                    } else {
                        fullStream = Stream.concat(employeeStream, fullStream);
                    }
                }
                employeeAvroSerialiser.serialise(fullStream, out);
            } catch (final Exception error) {
                error.printStackTrace();
            }

            long endTime = System.currentTimeMillis();
            LOGGER.info("Took " + (endTime - startTime) + "ms to create employees");
        }
    }

    private static void initializeworkLocation() throws IOException {
        File inputFile = new File(workLocationFilePath);
        BufferedReader br = new BufferedReader(new FileReader(inputFile));

        // get first line the title
        String inputLine = br.readLine();

        while ((inputLine = br.readLine()) != null) {

            //  populate file from csv file.
            StringTokenizer inputTokenizer = new StringTokenizer(inputLine, ";");

            String wlName = "";
            Address address = Address.generate(faker, random);

            if (inputTokenizer.hasMoreTokens()) {
                String tok = inputTokenizer.nextToken();
                // System.out.println("Token WLID :" + tok);
            }
            if (inputTokenizer.hasMoreTokens()) {
                String tok = inputTokenizer.nextToken();
                // System.out.println("Token WLName :" + tok);
                wlName = tok;
            }
            if (inputTokenizer.hasMoreTokens()) {
                String tok = inputTokenizer.nextToken();
                // System.out.println("Token null :" + tok);
            }
            if (inputTokenizer.hasMoreTokens()) {
                String tok = inputTokenizer.nextToken();
                // System.out.println("Token StreetNumber :" + tok);
                address.setStreetAddressNumber(tok);
            }
            if (inputTokenizer.hasMoreTokens()) {
                String tok = inputTokenizer.nextToken();
                // System.out.println("Token Street:" + tok);
                address.setStreetName(tok);
            }
            if (inputTokenizer.hasMoreTokens()) {
                String tok = inputTokenizer.nextToken();
                // System.out.println("Token area of city area:" + tok);

            }
            if (inputTokenizer.hasMoreTokens()) {
                String tok = inputTokenizer.nextToken();
                // System.out.println("Token area of city:" + tok);
                address.setCity(tok);
            }
            if (inputTokenizer.hasMoreTokens()) {
                String tok = inputTokenizer.nextToken();
                // System.out.println("Token area of state:" + tok);
                address.setState(tok);
            }
            workLocNameToAddressMap.put(wlName, address);
        }

    }

    /**
     *
     * @param args output folder path and Egeria git clone location
     * @return true if the arguments are valid. Otehrwise false with an error
     */
    private static boolean areArgsValid(final String[] args) {
        boolean isValid = true;
        if (args.length < 2) {
            LOGGER.error("This method needs two arguments. The directory path to save the files in and the directory where the Egeria source has been git cloned to (from https://github.com/odpi/egeria).");
            isValid = false;
        } else {
            outputFolderPath = args[0];
            if (!outputFolderPath.endsWith("/")) {
                outputFolderPath = outputFolderPath + "/";
            }
            String egeriaRoot = args[1];
            File f = new File(egeriaRoot);
            if (!(f.exists())) {
                LOGGER.error("The output folder does not exist");
                isValid = false;
            } else if (!f.isDirectory()) {
                LOGGER.error("The output folder is not a folder");
                isValid = false;
            } else {
                if (!egeriaRoot.endsWith("/")) {
                    egeriaRoot = egeriaRoot + "/";
                }
                salaryFilePath = egeriaRoot + "open-metadata-resources/open-metadata-deployment/sample-data/coco-pharmaceuticals/data-files/EmplSAnl-EmpSalaryAnalysis.csv";
                File salaryFile = new File(salaryFilePath);
                if (!salaryFile.exists()) {
                    LOGGER.error("Egeria source folder is not correct: cannot find " + salaryFilePath);
                    isValid = false;
                } else {
                    workLocationFilePath = egeriaRoot + "open-metadata-resources/open-metadata-deployment/sample-data/coco-pharmaceuticals/data-files/Location-WorkLocation.csv";
                    File wlFile = new File(workLocationFilePath);
                    if (!wlFile.exists()) {
                        LOGGER.error("Egeria source folder is not correct: cannot find " + workLocationFilePath);
                        isValid = false;
                    }
                }
            }
        }
        return isValid;
    }
}

