Comment Tag Gen Plugin 
======================
This is a Eclipse Plugin to generate java code. 

# Features #
* Your project code is used as a template.(A comment of java code and a spreadsheet is used for generating.) 
* Generating code could be merged. (It is not a Generation Gap Pattern but is a better.)
* You could customize a template or even logic for generating.The logic could make on your project. 
* Default is enum generator.

# Installation #

Eclipse Marketplace
http://marketplace.eclipse.org/content/comment-tag-gen

Eclipse update site: http://dl.bintray.com/ko2ic/comment-tag-gen/

Please check the fingerprint of a self signed certificate.
![thumbprint](https://raw.githubusercontent.com/ko2ic/ImageRepository/master/comment-tag-gen/thumbprint.png)


# Usage #
1. You write some information that have class name,comment and so on which you want to generate in the spreadsheet.  
Default enum generator assume:  
![ScreenShot](https://raw.githubusercontent.com/ko2ic/ImageRepository/master/comment-tag-gen/sampleExcel.png)  
1. You write a template code. It is a code which is used on your project. 
1. You add regular tag in comment on the template code.
1. Right click the spreadsheet in Package Explore or Project Explore and select "Run // Tag gen".Then will generate.

**Note:**  
"論理名" in Japanese means logical name.  
"物理名" in Japanese means physical name.  
"区分" in Japanese means classify.    
"説明" in Japanese  means explanation.

# Sample #
This sample is enum generator of default .

1. You get sample project.  
``git clone https://github.com/ko2ic/comment-tag-gen-plugin-sample``  
``cd  comment-tag-gen-plugin-sample``  
``mvn eclipse:eclipse``
1. You import sample project.
2. Plug-in is put in.

# Develop #

## Eclipse 

* download Eclipse Standard

* update site
m2e - http://download.eclipse.org/technology/m2e/releases/
* update
emf - update Modeling → 「EMF - Eclipse Modeling Framework SDK」

## lombok  

get lombok.jar

    $ git clone git@github.com:ko2ic/comment-tag-gen-common.git
    $ cd comment-tag-gen-common
    $ mvn eclipse:eclipse
    $ mvn install -Dmaven.test.skip=true

update specify eclipse with lombok    
double-click /.m2/repository/org/projectlombok/lombok/lombok-1.14.4.jar

## source

    $ git clone git@github.com:ko2ic/comment-tag-gen-plugin.git
    $ cd comment-tag-gen-plugin
    $ mvn eclipse:eclipse

import -> General -> Exsiting Projects into Workspace    
comment-tag-gen-plugin

import -> Maven -> Exsiting Maven Projects    
comment-tag-gen-common

You will need to add classpath of comment-tag-gen-common.jar in "comment-tag-gen-plugin.core" project when comiple error occure.

# License #
Eclipse Public License - v 1.0

# explain further:#
https://github.com/ko2ic/comment-tag-gen-plugin/wiki
