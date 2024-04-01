NOTE : Country is hardcoded as berlin germany, longitude:52.52, latitude 13.41
data i.e temperature is taken from 2000-01-01 onwards
Temp dataclass contain min and max temperaturs

API :
RestaPI instance (object) is created in RESTAPI.kt ,using interface from weatherAPI.kt
there are two Get methods for API
apiold : gives Temp of 2 days before of current date
apil2 : gives Temp of yesterday and today 
(due to API limitations)

Database : weather.kt , interface, entity, data class, abstract class
Entity -> data
interface ->wthrdao : contains methods for calling respective queries
            getfuturespec gives Temp of future dates by taking average of temps of 10 previous on the given data
            gethist : gives data directly from data table

Network connectivity : cknetobs.kt (class), cknet.kt(interface)
returns the current status of network in flow of Status as Available, unavailable, losing , lost

In main
(at the end)
WRepo class is build and created ,
it gives a single instance from get() method 
database Storetemp is created , and proper methods are defined for managing data

in oncreate:
network manager Ckneto is created 
repo is initlialized from Wrepo.get
bgscreen is called 

in bgscreen:
status is the network status, so when the network status is true , data using downloaddata, asyncdata and savedata is downloaded , and st is turned to true

proper text are placed
takeinput : gcalled input data which gives an otuline text field, and if the format of data is incorrect is stays red
correct format of date is 
year should be >= 2000
date should exist
and format "YYYY-MM-DD"

now if st is true ,i.e. data is downloaded, when we press get GET DATA , the proper results are shown 
make sure that Network and Download status are true
Github link :https://github.com/Vianshu/Weather_MC_A2_2021298.git


Gtihub
