<p align="center">
	<img src="https://github.com/tigaseinc/website-assets/blob/master/tigase/images/tigase-logo.png?raw=true" width="25px"/>
	<img src="https://tc.tigase.net/app/rest/builds/buildType:(id:TigaseUtils_Build)/statusIcon" width="100"/>
</p>

# What it is

Tigase Utils is a set of helper classes useful in building XMPP software, both server and client side.

# Features

* Handling of certificates
* Data structures & caches
* [XEP-0004: Data Forms](https://xmpp.org/extensions/xep-0004.html) support
* DNS resolution
* StringPrep
* Versions management & parsing
* JID & BareJID

# Support

When looking for support, please first search for answers to your question in the available online channels:

* Our online documentation: [Tigase Docs](https://docs.tigase.net/)
* Our online forums: [Tigase Forums](https://help.tigase.net/portal/community)
* Our online Knowledge Base [Tigase KB](https://help.tigase.net/portal/kb)

If you didn't find an answer in the resources above, feel free to submit your question to either our 
[community portal](https://help.tigase.net/portal/community) or open a [support ticket](https://help.tigase.net/portal/newticket).

# Downloads

Binaries can be downloaded from our [Maven repository](https://maven-repo.tigase.net/#artifact/tigase/tigase-utils)

You can easily add it to your project by including it as dependency:

```xml
<dependency>
  <groupId>tigase</groupId>
  <artifactId>tigase-utils</artifactId>
  <version>4.0.0</version>
</dependency>
```

# Using software

Please refer to [javadoc](https://docs.tigase.net/tigase-utils/master-snapshot/javadoc/)

# Compilation 

It's a Maven project therefore after cloning the repository you can easily build it with:

```bash
mvn -Pdist clean install
```

# License

<img alt="Tigase Tigase Logo"  src="https://github.com/tigaseinc/website-assets/blob/master/tigase/images/tigase-logo.png?raw=true" width="25px"/>This is official <a href="https://tigase.net/">Tigase</a> Repository.
Copyright (c) 2013-2019 Tigase, Inc.

Licensed under AGPL License Version 3. Other licensing options available upon request.
