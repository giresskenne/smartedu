FROM httpd

#updating the system
RUN apt update -y 

#variables
ARG port=80

#creating volume
VOLUME /saves

#copy from host to the container
COPY . /saves
#Installing useful packages
Run apt install -y unzip \
    vim 

#Container working directory
WORKDIR /usr/local/apache2/htdocs/

#Preparing the folder++
RUN rm -rf * 

#collecting the developer code
ADD   https://storage.googleapis.com/jfnhui/smartedu.zip .

#unzipping the directiory
RUN unzip smartedu.zip && \
    cp -r smartedu/* . && \
    rm -rf smartedu*

#expose the container
EXPOSE ${port}