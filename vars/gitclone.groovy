#!/usr/bin/env groovy

def call(Map getval) {
	checkout([$class: 'GitSCM', 
	branches: [[ name: getval.branch ]], 
	userRemoteConfigs: [[credentialsId: '12afdeaa-e9f3-4be8-bc17-64ccba068dbe', url: getval.url ]]
	])
  }
//12afdeaa-e9f3-4be8-bc17-64ccba068dbe
//Pipeline
