Joda Time Utils
=================
Utility library to  [Joda-Time](https://github.com/JodaOrg/joda-time) time in android, based on [Joda Time Android](https://github.com/dlew/joda-time-android) Andoird wrapper.

Why Joda-Time?
==============

Android has built-in date and time handling - why bother with a library?  If you've worked with Java's Date and Calendar classes you can probably answer this question yourself, but if not, check out [Joda-Time's list of benefits](http://www.joda.org/joda-time/#Why_Joda-Time).

For Android developers Joda-Time solves one critical problem: stale timezone data.  Built-in timezone data is only updated when the OS is updated, and we all know how often that happens.  [Countries modify](http://www.bbc.co.uk/news/world-europe-15512177) [their timezones](http://www.heraldsun.com.au/news/breaking-news/samoa-to-move-the-international-dateline/story-e6frf7jx-1226051660380) [all the](http://www.indystar.com/apps/pbcs.dll/article?AID=/20070207/LOCAL190108/702070524/0/LOCAL) [time](http://uk.reuters.com/article/oilRpt/idUKBLA65048420070916); being able to update your own tz data keeps your app up-to-date and accurate.

Why This Library?
=================

Joda-Time is really useful, but some thing were missing when using it on Android:
- a DatePicker that works with DateTime
- a DateragePicker that works with DateTime
- a Gson Serializer/Deserializer fot DateTime

In this library you can find all of them

Integration
=====

Add the following dependency to `build.gradle`:

```groovy
dependencies {
    compile 'TBD'
}
```
Then follow the initializations instructions from [joda-time-adnroid](https://github.com/dlew/joda-time-android#usage)
