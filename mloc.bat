::echo %*
::java -cp "cli/build/libs/cli-1.0.jar" "br.com.brunodelima.mocklocation.StartCLI" $*
::java -jar "cli/build/install/cli/lib/cli-1.0.jar" %*
@call cli/build/install/cli/bin/cli.bat %*