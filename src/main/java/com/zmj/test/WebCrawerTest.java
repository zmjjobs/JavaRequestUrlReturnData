package com.zmj.test;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.zmj.crawler.ColorfyCrawler;
import com.zmj.crawler.CwapiCrawler;

public class WebCrawerTest {
	public static void main( String[] args ) {
        Options options = new Options();
        options.addOption("fetchData", true, "抓取网页数据");
        options.addOption("filePath", true, "存储文件的位置");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            String filePath = null;
            if (cmd.hasOption("filePath")) {
                filePath = cmd.getOptionValue("filePath");
                if (cmd.hasOption("fetchData")) {
                    switch (cmd.getOptionValue("fetchData")) {
                        case "cwapi" :
                            System.out.println(CwapiCrawler.run(filePath));
                            break;
                        case "colorfy" :
                            System.out.println(ColorfyCrawler.run(filePath));
                            break;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
