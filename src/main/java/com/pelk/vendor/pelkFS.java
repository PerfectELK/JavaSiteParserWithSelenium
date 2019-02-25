package com.pelk.vendor;

import java.io.*;

public class pelkFS {


    private String source;
    private String dest;
    private String destExtension;
    private String currentDir = System.getProperty("user.dir");

    public pelkFS(String source, String dest){
        this.source = source;
        this.dest = dest;
    }

    public String getDest() {
        return this.dest;
    }

    public void createDest(){


        this.dest = this.currentDir+"\\"+this.dest+"."+this.getExtension(this.source);

        try {
            File dest = new File(this.dest);
            dest.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getExtension(String file){

        String extension = "";

        int i = file.lastIndexOf('.');
        if (i >= 0) {
            extension = file.substring(i+1);
        }
        return extension;
    }

    public void copyFileUsingStream() throws IOException {

        InputStream is = null;
        OutputStream os = null;

        try {
            is = new FileInputStream(new File(this.source));
            os = new FileOutputStream(new File(this.dest));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

}
