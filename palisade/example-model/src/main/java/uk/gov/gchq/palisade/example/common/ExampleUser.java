/*
 * Copyright 2019 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.gov.gchq.palisade.example.common;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import uk.gov.gchq.palisade.ToStringBuilder;
import uk.gov.gchq.palisade.User;

import static java.util.Objects.requireNonNull;

public class ExampleUser extends User {

    private String name;
    private String email;
    private String dn;

    public ExampleUser(final User user) {
        setUserId(user.getUserId());
        setAuths(user.getAuths());
        setRoles(user.getRoles());
    }

    public ExampleUser name(final String name) {
        requireNonNull(name);
        this.name = name;
        return this;
    }

    public ExampleUser email(final String email) {
        requireNonNull(email);
        this.email = email;
        return this;
    }

    public ExampleUser dn(final String dn) {
        requireNonNull(dn);
        this.dn = dn;
        return this;
    }

    public ExampleUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        name(name);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        email(email);
    }

    public String getDn() {
        return dn;
    }

    public void setDn(final String dn) {
        dn(dn);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ExampleUser exampleUser = (ExampleUser) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(name, exampleUser.name)
                .append(email, exampleUser.email)
                .append(dn, exampleUser.dn)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 19)
                .appendSuper(super.hashCode())
                .append(name)
                .append(email)
                .append(dn)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("name", name)
                .append("email", email)
                .append("dn", dn)
                .toString();
    }
}
