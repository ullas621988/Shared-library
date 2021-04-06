#!/usr/bin/env groovy

def call(Map getval) {
	checkout([$class: 'GitSCM', 
	branches: [[ name: getval.branch ]], 
	userRemoteConfigs: [[credentialsId: '78c8965c-0a19-4076-ac1e-43cca4780423', url: getval.url ]]
	])
  }
//78c8965c-0a19-4076-ac1e-43cca4780423
//Pipeline
