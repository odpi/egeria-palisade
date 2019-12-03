FROM jupyter/base-notebook:latest

USER root
RUN apt-get update
RUN apt-get install default-jdk -y
ADD docker/bin/ /home/jovyan
RUN chown jovyan:users /home/jovyan/query.sh
RUN chmod 744 /home/jovyan/query.sh

USER jovyan
