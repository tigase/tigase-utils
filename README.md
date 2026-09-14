<p align="center">
  <img src="https://github.com/tigaseinc/website-assets/blob/master/tigase/images/icons8-maintenance.png?raw=true" width="100px"/>
  <br/>
	<img src="https://github.com/tigaseinc/website-assets/blob/master/tigase/images/tigase-logo.png?raw=true" width="25px"/>
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

* Our online documentation: [Tigase Docs](https://docs.tigase.net)
* Existing issues in relevant project, for Tigase Server it's: [Tigase XMPP Server GitHub issues](https://github.com/tigase/tigase-server/issues)

If you didn't find an answer in the resources above, feel free to submit your question as new __issue on GitHub__ or, if you have valid support subscription, open [new support ticket](https://tigase.net/technical-support).

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

<img alt="Tigase Tigase Logo" src="https://github.com/tigase/website-assets/blob/master/tigase/images/tigase-logo.png?raw=true" width="25"/> Official <a href="https://tigase.net/">Tigase</a> repository is available at: https://github.com/tigase/tigase-utils/.

Copyright (c) 2004 Tigase, Inc.

Licensed under AGPL License Version 3. Other licensing options available upon request.
