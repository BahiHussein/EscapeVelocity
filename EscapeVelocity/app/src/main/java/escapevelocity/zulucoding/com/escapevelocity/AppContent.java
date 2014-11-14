package escapevelocity.zulucoding.com.escapevelocity;

public class AppContent {
	
// Type of the Application
	
/*[1] Offline Application*/								public static final Boolean offline_app = false;
/*[2] One Page Application*/							public static final Boolean one_page_app = false;
/*[3] One Page Application*/							public static final Boolean fullscreen_webview = false;


//===================================APP ADMIN and URLS=======================================

/*[A1]Admin Name ( Used for testing )*/     			public static final String admin_name = "admin";  
/*[A2]Mobile website or web based mobile app URL*/	    public static final String mobile_website_url = "Your Website URL"; /*<<=========Important
/*[A3]Registration php file URL*/               		public static final String php_url = "Your PHP URL"; /*<<=========Important
/*[A4]if it is null, browser's URL will be shared*/     public static final String share_url = "null";
/*[A5]contact page URL*/               		            public static final String contact_url = "Contact Us URL"; /*<<=========Important
/*[A6]about page URL*/               		            public static final String about_url = "About Us URL"; /*<<=========Important
/*[A7]Save Last link WebView opens last visited link*/	public static final boolean save_url = true;

//========================================MESSAGES=============================================

/*[B1]Welcome message shows only one time*/     		public static final String welcome_url = "WELCOME TO Your App"; /*<<=========Important
/*[B2]Connection issues message*/                       public static final String connection_error_message = "Connection Error! please try later";
/*[B3]Message shows before the shared link*/			public static final String share_message = "Check Out this link: ";
/*[B4]Invalid mobile number error message*/		        public static final String invalid_mobile_number_error_message = "Please enter valid mobile number";
/*[B5]Invalid name error message like(name=a)*/			public static final String invalid_name_error_message = "please enter valid name";
/*[B6]Invalid name error message like(name=a)*/			public static final String invalid_email_error_message = "Please enter valid email";
/*[B7]Empty field error message*/	                	public static final String empty_fields_error_message = "please enter all fields";
/*[B7]Empty field error message*/	                	public static final String username_is_too_short_error_message = "Username is too short!";
/*[B7]Empty field error message*/	                	public static final String username_is_too_long_error_message = "Username is too long!";
/*[B7]Empty field error message*/	                	public static final String username_should_be_letters_only_error_message = "Username should by only letters";
/*[B8]Loading message*/	                             	public static final String loading_message = "Loading...";
//==========================================SETTINGS================================================

/*[C2]To cancel registration but "no" lower case*/      public static final boolean registration_form_active = false; /*<<=========Important
/*[C3]The minimum number of characters for name*/       public static final int username_minimum_character_length = 3;
/*[C4]The maximum number of characters for name*/       public static final int username_maximum_character_length = 20;
/*[C5]The minimum character length for mobile number*/  public static final int user_mobile_miniumum_number_length = 8; 
/*[C6]The maximum character length for mobile number*/  public static final int user_mobile_maximum_number_length = 15; 
//==========================================RateME================================================	

/*[D1]Application Name( Used for Rating )*/     		public static final String application_name = "Your App Name"; /*<<=========Important
/*[D2]Package Name(Example com.example.appname)*/     	public static final String package_name  = "com.yourcompany.appname";  /*<<=========Important 
/*[D3]Number of lunches before Rate me message shows*/ 	public static final int number_of_uses_before_launching_the_rate_dialog = 1;
/*[D4]To deactivate RateDialog change "yes" to "no"*/ 	public static final boolean rate_dialog_active = true;
//==========================================Admob================================================

/*[E1]please replace it with you Admob Banner ID*/     	public static final String google_play_ads_banner_id = "Admob banner id";
/*[E2]To deactivate Admob change "yes" to "no"*/     	public static final Boolean google_play_ads_banner_active = true;
/*[E3]please replace it with you Admob Interstitial ID*/public static final String google_play_ads_interstitial_id = "Admob Interstitial id";
/*[E4]Deactivate InterstitialAd change "yes" to "no"*/  public static final Boolean google_play_ads_interstitial_active = true;

//==========================================Forced UI================================================
/*[E1]Hide HintText on focus (on the registration form)*/public static final Boolean hide_hint_on_focus = false;






}
