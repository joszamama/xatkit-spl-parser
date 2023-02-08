package com.xatkit.spl.parser;

public class XatkitParser 
{
    public static void main( String[] args )
    {
        // IMPORTANT:
        // The path must be relative to the project root
        // It must be a JSON from Xatkit-SPL to keep the same structure
        // It must not include the file extension, only the name
        //
        // Example:
        // String path = ".\\src\\bots\\ExampleBot\\ExampleBot";
        // InstanceFile.compile(path);


            String path = ".\\src\\bots\\ExampleBot\\ExampleBot";
            InstanceFile.compile(path);
        }
    }
