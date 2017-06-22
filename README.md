# SignupApp

A compiled APK of this app to install on an Android device can be downloaded here:
http://geoffledak.com/signupapp/signupapp2.apk

A hardcoded default image will be shown if no avatar url is entered. If an avatar url is entered, the image can be tapped to see a larger version of it.

If a favorite food is entered, a list will be populated at the bottom of the Profile view with pictures of said food.

The images are being pulled from the flickr api:
http://api.flickr.com/services/feeds/photos_public.gne?nojsoncallback=1&tagmode=any&format=json&tags=

^^ The specified food is appended onto the end of the url as a tag.

Images in the list can be tapped to see a bigger version of them.

If favorite food is left blank, a placeholder view will be shown.


### Screenshots

![Edit Profile](/screenshots/screenshot1.png "Edit Profile") ![View Profile](/screenshots/screenshot2.png "View Profile")
![Avatar Large](/screenshots/screenshot3.png "Avatar Large") ![Image Detail](/screenshots/screenshot4.png "Image Detail")