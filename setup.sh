#!/bin/bash
alias deploy='mvn package; cp target/*.war /var/lib/tomcat9/webapps/'
