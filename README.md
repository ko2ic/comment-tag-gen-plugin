Comment Tag Gen Plugin 
======================
This is a Eclipse Plugin to generate java code. 

# Features #
* Your project code is used as a template.(A comment of java code and a spreadsheet is used for generating.) 
* Generating code could be merged. (It is not a Generation Gap Pattern but is a better.)
* You could customize a template or even logic for generating.The logic could make on your project. 
* Default is enum generator.

# Installation #

Eclipse update site: http://dl.bintray.com/ko2ic/comment-tag-gen/

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

# License #
Eclipse Public License - v 1.0

# explain further:#
https://github.com/ko2ic/comment-tag-gen-plugin/wiki
