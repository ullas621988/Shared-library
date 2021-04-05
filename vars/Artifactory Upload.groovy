#!/usr/bin/env groovy

def call () {
	rtUpload (
		serverId: 'central',
		spec: '''{
		"files": [
						{
							"pattern": "target/*.war",
							"target": "Demo-Upload/$JOB_NAME/$BUILD_NUMBER/"
						}
					]
				}''',
			buildName: 'Pipeline-Groovy'
		)
}
