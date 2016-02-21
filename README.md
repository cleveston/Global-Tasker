Tasking Assignment for Multiple Mobile Devices (Mobile Grid Computing) 
 
Iury Cleveston 
Lucas Bortolini Fronza 
 
The problem 
 
We know that each year the number of mobile devices increase. Today, each person 
has at least one device and these devices are in idle mode most of the time. 
Besides that, there are computational problems that need more processing power like 
rendering videos, applying filters to images and mathematical calculations of various kinds. If 
we could use these idle devices to make these tasks, we would be able to solve these tasks in a  
shorter time. 
 
Our solution 
 
Our idea is to use these available computational resources by creating a network 
between mobile devices, in which of these devices receives data packages to be processed. 
So, first, the users will upload an array to be sorted by multiple devices. A cloud 
application will receive this task and assign to other connected devices to make the 
processing. When the processing ends the device send the result back to the cloud application. 
 
The implementation 
 
Our implementation architecture is client/server, where we have a cloud permanently 
running and receiving tasks from mobile devices. For each task received the cloud distribute 
to mobile devices that are connected to it. 
The cloud will act as a Web Service, receiving requests from mobile devices and 
returning data in JSON format or in the respective format of the processed data. 
This application will be implemented for Android devices, so written in Java. From 
the App, the user will be able to view the tasks that the device is running and submit new 
tasks to the cloud. 
