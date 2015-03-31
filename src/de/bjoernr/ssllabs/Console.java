package de.bjoernr.ssllabs;

import de.bjoernr.ssllabs.ConsoleUtilities;

import java.util.Arrays;
import java.util.Map;

import org.json.JSONObject;

/**
 * Command line interface class
 * 
 * @author Björn Roland <https://github.com/bjoernr-de>
 */
public class Console {

	public static void main(String[] args) 
	{
		printHeader();
		
		if(args.length == 1 && (args[0].equals("--info") || args[0].equals("-i")))
		{
			handleInfo();
		}
		else
		{
			printUsage();
		}
	}
	
	public static void handleInfo()
	{
		Api ssllabsApi = new Api();
		
		JSONObject apiInfo = ssllabsApi.fetchApiInfo();
		Map<String, Object> map = ConsoleUtilities.jsonToMap(apiInfo);
		
		System.out.println("API information");
		System.out.println("");
		System.out.println(ConsoleUtilities.mapToConsoleOutput(map));
	}
	
	public static void printHeader()
	{
		System.out.println("");
		System.out.println("   ___                    _____ _____ _      _           _            ___  ______ _____ ");
		System.out.println("  |_  |                  /  ___/  ___| |    | |         | |          / _ \\ | ___ \\_   _|");
		System.out.println("    | | __ ___   ____ _  \\ `--.\\ `--.| |    | |     __ _| |__  ___  / /_\\ \\| |_/ / | |  ");
		System.out.println("    | |/ _` \\ \\ / / _` |  `--. \\`--. \\ |    | |    / _` | '_ \\/ __| |  _  ||  __/  | |  ");
		System.out.println("/\\__/ / (_| |\\ V / (_| | /\\__/ /\\__/ / |____| |___| (_| | |_) \\__ \\ | | | || |    _| |_ ");
		System.out.println("\\____/ \\__,_| \\_/ \\__,_| \\____/\\____/\\_____/\\_____/\\__,_|_.__/|___/ \\_| |_/\\_|    \\___/ ");
		System.out.println("by Björn Roland <https://github.com/bjoernr-de>");
		System.out.println("and contributors (https://github.com/bjoernr-de/java-ssllabs-api/graphs/contributors)");
		System.out.println("-------------------------------------------------");
		System.out.println("");
	}
	
	public static void printUsage()
	{
		String jarName = "java-ssllabs-api-" + Api.getVersion() + ".jar";
		String jarExecution = "java -jar " + jarName;
		
		System.out.println("Help");
		System.out.println(jarExecution);
		System.out.println("");
		
		System.out.println("-i, --info");
		System.out.println("	Fetch API information");
	}
}