/*
 * Lukas Ruge
 * Released to the public domain
 */


package de.lukeslog.casinobot;


import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Twitter bot that tweets what there is to eat
 * 
 * http://open.spotify.com/track/22S12mWjS3iC1caOq6Kp0c
 * 
 * @author lukas
 *
 */
public class TwitterBot implements Runnable 
{

	private static final int NUMBER_OF_WEEKS = 4;
	
	boolean even = false;
	
	private String OAUTH_CONSUMER_KEY= OAuthData.OAUTH_CONSUMER_KEY; //not in the repository for obvious reasons
	private String OAUTH_CONSUMER_SECRET =OAuthData.OAUTH_CONSUMER_SECRET;//not in the repository for obvious reasons
	private String OAUTH_ACCESS_TOKEN = OAuthData.OAUTH_ACCESS_TOKEN;//not in the repository for obvious reasons
	private String OAUTH_ACCESS_TOKEN_SECRET = OAuthData.OAUTH_ACCESS_TOKEN_SECRET;//not in the repository for obvious reasons
	
	private static final String SONDERMENU_URL = "http://www.uk-sh.de/uksh_media/Speisepl%C3%A4ne/L%C3%BCbeck+_+Sondermen%C3%BC+/Sondermen%C3%BC+KW+";
	
    //Woche 1
	private static final String T_11="1. Schinkennudeln + Tomatensauce; 2. Kartoffelauflauf, Schnittlauchsauce, Broccoli. Suppe: Gemüse";
	private static final String T_12="1. Seelachsfilet, Dillsauce, Blattspinat, Kartoffeln. 2. Champignons, Spagetti, Käsesahnesauce. Suppe: Kräuter";
	private static final String T_13="1. Schweinabraten, Rotkohl, Kartoffel; 2. Blumenkohl-Käsebratling. Kartoffeln. Kräutersauce. Suppe: Broccoli";
	private static final String T_14="1. Rindergeschnetzeltes, Champignons, Spätzle; 2. Fagottini, Gemüsestreifen, Broccoli, Tomatensauce. Suppe: Champ.";
	private static final String T_15="1. Putenbraten, Rustika-Gemüse; 2. Milchreis mit Kirschsauce. Suppe: Kartoffelsuppe.";
	//Woche 2
	private static final String T_21="1. Tortellini mit Rind und Paprikastreifen; 2. Sellerie-Knusperschnitzel mit Reis & Tomatensauce. Suppe: Möhren.";
	private static final String T_22="1. Hackbraten vom Rind mit Bohnen; 2. Saccotini Rucola mit Blattspinat. Tagessuppe. Blumenkohl";
	private static final String T_23="1. Hähnchenbrustfiletspieß & Champignonsauce mit Gemüse; 2. Grießbrei mit Fruchtsauce. Tagessuppe: Gemüse";
	private static final String T_24="1. Sauerbraten, Rosinensauce, Rotkohl, Kartoffelklöße; 2. Buonfatti Mediterran buntes Gemüse, Tomatensauce. Suppe: Spinat";
	private static final String T_25="1. ged. Fischfilet, Senfsauce, Petersilienkartoffeln; 2. Maultaschen mit Spinat im Gemüsesud. Tagessuppe: Spargel";
	//Woche 3
	private static final String T_31="1. Hähnchenbrustfilet, S. Hollandaise, Romanogemüse; 2. Kartoffeltaschen mit Frischkäse, Broccoli. Suppe: Gemüse.";
	private static final String T_32="1. Königsberger Klopse, Kapernsauce, Petersilienkart. 2. Vegetarisches Chili";
	private static final String T_33="1. Bolognese Gabelspaghetti; 2. Veg. Bratwurst, Erbsen-Maisgemüse in Rahm, Kartoffelpü. Suppe: Broccoli";
	private static final String T_34="1. Geflügelfrikadelle, Kohlrabi in Rahm, Petersilienkartoffeln. 2. Frühlingsrolle mit Reis. Tagessuppe: Champignon";
	private static final String T_35="1. Bratwurst, Majoransauce, Erbsen-Karotten-Püree; 2. Spätzle-Gemüsepfanne mit Käsesauce. Suppe: Kartoffel";
	//Woche 4
	private static final String T_01="1. Hähnchenbrustfilet, Kaisergemüse, Petersilienkart.; 2. Vollkorn-Pilzbratling, Zitronensauc. Gemüsereis. Suppe: Möhren.";
	private static final String T_02="1. Fischfilet, Remouladensauce, Majonäse-Kartoffelsalat; 2. Spagetti Napoli mit Parmesan. Suppe: Blumenkohl.";
	private static final String T_03="1. Paniertes Schweineschnitzel mit Nudeln; 2. Spinat-Omlett mir Zwiebelsauce, Röstkartoffeln. Suppe: Gemüse";
	private static final String T_04="1. Gulasch vom Schwein, Erbsen, Spirelli; 2. Gnocci mit Tomaten- Paprikasauce. Suppe: Spinat";
	private static final String T_05="1. Hackbällchen, Pilzrahmsauce, Gemüsereis, Kartoffeln; 2. Champignon-Blätterteig-Tasche. Suppe: Spargel.";
	//hasmap for the food strings...
	private static Map<String, String> foodMap = new HashMap<String, String>();
	
    public static void main(String args[]) 
    {
    	//build foodmap
    	configurefoodMap();
    	//starting a thread to run forever
    	(new Thread(new TwitterBot())).start();
    }
	
	public void run() 
	{
		boolean run = true;
		
		while(run)
		{		
			DateTime d = new DateTime();//update the dateTime
			//getWeekNumber(d);
			//System.out.println(h()+" "+m());
			//es ist Zeit die heutige malzeit zu tittern.
			//System.out.println(d.getMonthOfYear()+", "+d.getDayOfMonth());
			if(d.getMonthOfYear()==4 && d.getDayOfMonth()==1)
			{
				if(h(d)==7 && m(d)==30) //if its 7:30
				{
					tweet("Heute kocht Jamie Oliver nur für @Flasher1984!");
					tweet("Sondermenü: http://www.youtube.com/watch?v=oHg5SJYRHA0");
				}
			}
			if(d.getMonthOfYear()==12 && d.getDayOfMonth()==20)
			{
				if(h(d)==13 && m(d)==0) //if its 13:00
				{
					tweet("Ich bin raus. Happy Hollidays Bitches.");
				}
			}
			else
			{
				
				if(!isHollyday(d) && !isWeekEnd(d)) //not if today is a hollyday or weekend
				{
					if(h(d)==7 && m(d)==30) //if its 11:00
					{
						tweet("Heute "+getTodaysMeal(d));
						tweet(sondermenue(d));
					}
					if((h(d)==11 && m(d)==0))
					{
						tweet("11am Update "+getTodaysMeal(d));
						tweet("+"+sondermenue(d));
					}
				}
				//es ist Zeit die morgige malzeit zu twittern
				if(!isDayBeforeHollyday(d) && !tommorowIsWeekEnd(d))//not if tommorrow is a hollyday
				{
					if(h(d)==15 && m(d)==00) //if its 15:00
					{
						tweet("Morgen "+getTommorowsMeal(d));
					}
				}
			}
			d=null;
			try 
			{
				Thread.sleep(59555); //sleep
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Determines the number of the week according to the Casino plan
	 * @return
	 */
	private int getWeekNumber(DateTime d)
	{
		DateTime dt = new DateTime("2012-07-30T00:00:00.000+01:00"); //the week where the new Casino Plan started.
		//System.out.println(dt.getMillis());
		dt.getDayOfMonth();
		int x = Days.daysBetween(dt, d).getDays();
		x=x/7+1;
		x=x%NUMBER_OF_WEEKS;
		return x;	
	}
	
	private int kw(DateTime d)
	{
		return d.getWeekOfWeekyear();
	}
	
	private int m(DateTime d)
	{
		return d.getMinuteOfHour();
	}
	
	private int h(DateTime d)
	{
		return d.getHourOfDay();
	}
	
	private int dayoftheweek(DateTime d)
	{
		//System.out.println(d.getDayOfWeek());
		return d.getDayOfWeek();
	}
	
	/**
	 * returns todays meal as a string
	 * 
	 * @return
	 */
	private String getTodaysMeal(DateTime d)
	{
		String meal = foodMap.get(""+getWeekNumber(d)+dayoftheweek(d));
		return meal;
	}
	
	/**
	 * retuns tommorrows meal as a string
	 * 
	 * @return
	 */
	private String getTommorowsMeal(DateTime d)
	{
		d=d.plusDays(1);
		return getTodaysMeal(d);
	}
	
	/**
	 * is it a weekned
	 * 
	 * @return true if today is either saturday or sunday
	 */
	private boolean isWeekEnd(DateTime d)
	{
		if(dayoftheweek(d)==6 || dayoftheweek(d)==7)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * is tommorow a weekend
	 * 
	 * @return true if tommorow is either saturday or sunday
	 */
	private boolean tommorowIsWeekEnd(DateTime d)
	{
		d=d.plusDays(1);
		return isWeekEnd(d);
	}

	/**
	 * Chesk if today is a hollyday
	 * 
	 * @return true iof today is a hollyday and tzhe casino is closed
	 */
	private boolean isHollyday(DateTime d)
	{
		boolean hollyday=false;
		//tag der deutschen einheit
		if(d.getDayOfMonth()==3 && d.getMonthOfYear()==10)
		{
			hollyday=true;
		}
		//weihnachten
		if(d.getDayOfMonth()==24 && d.getMonthOfYear()==12)
		{
			hollyday=true;
		}
		if(d.getDayOfMonth()==25 && d.getMonthOfYear()==12)
		{
			hollyday=true;
		}
		if(d.getDayOfMonth()==26 && d.getMonthOfYear()==12)
		{
			hollyday=true;
		}
		//silvester und Neujahr
		if(d.getDayOfMonth()==31 && d.getMonthOfYear()==12)
		{
			hollyday=true;
		}
		if(d.getDayOfMonth()==1 && d.getMonthOfYear()==1)
		{
			hollyday=true;
		}
		//tag der arbeit
		if(d.getDayOfMonth()==1 && d.getMonthOfYear()==5)
		{
			hollyday=true;
		}
		return hollyday;
	}
	
	/**
	 * Checks if today is the day before a holoday
	 * 
	 * @return true if tommorow is a hollday (and thus the casino is closed)
	 */
	private boolean isDayBeforeHollyday(DateTime d)
	{
		//tag der deutschen einheit
		d=d.plusDays(1);
		return isHollyday(d);
	}
	
	/**
	 * Generates the Sonedmedue URL for the PDF (does not check if said pdf has actually been uploaded by the uksh already)
	 * 
	 * @return
	 */
	private String sondermenue(DateTime d)
	{
		return "Sondermenü: "+SONDERMENU_URL+kw(d)+".pdf";
	}

	/**
	 * Tweets a text using the credetials (does not tweet emoty text)
	 * 
	 * @param text
	 */
	private void tweet(String text)
	{
		
		if(!(text.equals("")))
		{
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setOAuthConsumerKey(OAUTH_CONSUMER_KEY);
			cb.setOAuthConsumerSecret(OAUTH_CONSUMER_SECRET);
			cb.setOAuthAccessToken(OAUTH_ACCESS_TOKEN);
			cb.setOAuthAccessTokenSecret(OAUTH_ACCESS_TOKEN_SECRET);

			//Abfrage starten
			Twitter twitter = new TwitterFactory(cb.build()).getInstance();
			try 
			{
				Status status = twitter.updateStatus(text);
				//System.out.println("tweet "+text);
			    System.out.println("Successfully updated the status to ["+ status.getText() +" ].");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private static void configurefoodMap()
	{
		foodMap.put("11", T_11);
    	foodMap.put("12", T_12);
    	foodMap.put("13", T_13);
    	foodMap.put("14", T_14);
    	foodMap.put("15", T_15);
    	foodMap.put("21", T_21);
    	foodMap.put("22", T_22);
    	foodMap.put("23", T_23);
    	foodMap.put("24", T_24);
    	foodMap.put("25", T_25);
    	foodMap.put("31", T_31);
    	foodMap.put("32", T_32);
    	foodMap.put("33", T_33);
    	foodMap.put("34", T_34);
    	foodMap.put("35", T_35);
    	foodMap.put("01", T_01);
    	foodMap.put("02", T_02);
    	foodMap.put("03", T_03);
    	foodMap.put("04", T_04);
    	foodMap.put("05", T_05);
	}
}
