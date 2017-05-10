# DynamoDb setup :
* Download dynamoDb-local.
* Run dynamoDb using "java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb".
* For dynamoDb credentials, create a file "~/.aws/credentials" with content :
[default]
aws_access_key_id=fake
aws_secret_access_key=fake

# Intellij setup :
* Clone the project using "git clone https://github.com/nishantbhardwaj2002/reco.git".
* Import the project folder "reco" with intellij.
* Add java-ee and spring from plugin support.
* Add the following JARs in project-stucture -> module -> dependencies :
    * mallet 2.0.8 (JARs in dist and lib directory).
    * aws-sdk 1.11.125 (JARs in lib and third-party directory).
    * gson 2.8.0
    * jbcrypt 0.4
    * commons-math3 3.6.1
    * guava 21.0
    * jsoup 1.10.2
* From run -> edit-configuration, create a tomcat server configuration using apache-tomcat 8.5.11 JAR.

# Data
* Dataset for LDA training : http://mlg.ucd.ie/datasets/bbc.html
* Newsfeed template : http://www.free-css.com/free-css-templates/page212/hydrogen
* Signin/signup style css : https://codepen.io/colorlib/pen/rxddKy

# Problems :
* If tomcat complains that "tomEE required to support EAR/EJB deplyment" : http://stackoverflow.com/questions/25630054/error-tomee-required-to-support-ear-ejb-deployment-error-message/43791825#43791825
