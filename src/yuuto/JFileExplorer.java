package yuuto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
 
public class JFileExplorer
{
	public static void main(String[] args) throws IOException
	{
		System.out.println("------------------------------------------");
		System.out.println("---     1. Search Files by Keyword    ---");
		System.out.println("---     2. Search Files by Type        ---");
		System.out.println("---     3. Copy File/Folder            ---");
		System.out.println("---     4. Return                      ---");
		System.out.println("------------------------------------------");
		
		Scanner in = new Scanner(System.in);
		int n = 0;
		String firstInput, secondInput;
		while(true) {
			System.out.println();
			try {
			n = in.nextInt();
			}catch(Exception e) {
				System.out.print("Error! Try again:");
				continue;
			}
			switch (n){
			case 1:
				System.out.println("Please input the file address you wish to search for: ");
				firstInput = in.next();
				System.out.print("Please input the keyword: ");
				secondInput = in.next();
				FileManager.searchKeyword(firstInput, secondInput);
				break;
			case 2:
				System.out.println("Please input the file address you wish to search for:");
				firstInput = in.next();
				System.out.print("Please input the file type: ");
				String tail = in.next();
				String[] spilttail = tail.split(",");
				FileManager.searchType(firstInput, spilttail);
				break;
			case 3:
				System.out.println("PLease input the source address: ");
				firstInput = in.next();
				System.out.println("PLease input the target address: ");
				secondInput = in.next();
				FileManager.copyFile(firstInput, secondInput);
				break;
			case 4:
				System.exit(0);
				break;
			default:
				System.out.println("Error! Try again:");
				break;
			}
		}
	}
}
class FileManager
{
	public static void searchKeyword(String pathName, String keyWord) throws IOException {
		
		File dirFile = new File(pathName);
		if(!dirFile.exists()) {
			System.out.println("There is no file's name matching the keyword!");
			return;
		}
		if(!dirFile.isDirectory()) {
			if(dirFile.isFile()) {
				String str = dirFile.getName();
				if(str.indexOf(keyWord) != -1);
				System.out.println(dirFile.getCanonicalFile());
			}
			return;
		}
		if(dirFile.getName().indexOf(keyWord) != -1)
			System.out.println(dirFile.getCanonicalPath());
		String[] filelist = dirFile.list();
		for(int i = 0;i < filelist.length; ++i) {
			String string = filelist[i];
			File file = new File(dirFile.getPath(),string);
			String name = file.getName();
			if(file.isDirectory()) {
				searchKeyword(file.getAbsolutePath(), keyWord);
			}
			else {
				if(name.indexOf(keyWord) != -1)
				System.out.println(file.getCanonicalFile());
			}
		}
	}
 
	public static void searchType(String pathName, String[] fileType) throws IOException
	{
		File dirFile=new File(pathName);
		if(!dirFile.exists()) {
			System.out.println("There is no matching file tpye!");
			return;
		}
		if(!dirFile.isDirectory()) {
			if(dirFile.isFile()) {
				String str = dirFile.getName();
				String tail = str.substring(str.lastIndexOf(".")+1);
				for(int i = 0; i < fileType.length; ++i) {
					if(tail.equals(fileType[i]))
						System.out.println(dirFile.getCanonicalFile());
				}
			}
			return;
		}
		String[] filelist=dirFile.list();
		for(int i = 0;i < filelist.length; ++i) {
			String string = filelist[i];
			File file = new File(dirFile.getPath(),string);
			String name = file.getName();
			if(file.isDirectory()) {
				searchType(file.getAbsolutePath(), fileType);
			}
			else {
				String tail = name.substring(name.lastIndexOf(".") + 1);
				for(int j = 0; j < fileType.length; ++j) {
					if(tail.equals(fileType[j]))
				    System.out.println(file.getCanonicalFile());
				}
			}
		}
	}
 
	public static void copyFile(String fromPath,String toPath) throws IOException {
		File source = new File(fromPath);
		if(!source.exists()) {
			System.out.println("Can't find " + fromPath + "!");
			return;
		}
		File des = new File(toPath);
		if(!des.exists())
		{
			des.mkdir();
		}
		File[] file=source.listFiles();
		FileInputStream input = null;
		FileOutputStream output = null;
		for(int i = 0; i < file.length; ++i)
		{
			try {
				if(file[i].isFile()) {
					input = new FileInputStream(file[i]);
					output = new FileOutputStream(new File(toPath+"/"+file[i].getName()));
					byte[] b = new byte[1024];
					int len;
					while((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
				input.close();
				output.flush();
				output.close();
				}
				else if(file[i].isDirectory()) {
					copyFile(fromPath + "/" + file[i].getName(), toPath + "/" + file[i].getName());
				}
			}catch(FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		if(output!=null)
			output.close();
	}
}
