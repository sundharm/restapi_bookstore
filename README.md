# restapi_bookstore
CRUD for simple bookstore

Download/Clone the Repo

To Run the application

    1. Go to the project folder in terminal

    2. Enter the following command to build the docker image
          docker build -f Dockerfile -t bookstore-restapi .

    3. Run the docker image using the below command
          docker run -p 8080:8080 bookstore-restapi
      
The application will now run and listen at localhost:8080

The APIs can be tested using Swagger at
    http://localhost:8080/swagger-ui.html 

(Please check README.docx for more details)
    



