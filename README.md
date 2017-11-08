# Chamados

install scene builder 8<br>
install eclipse (javafx)<br>
install java 8<br>
install mysql<br>

<br>
Colocar o .classpath e .project no projeto:
<br>

.classpath

```xml
<?xml version="1.0" encoding="UTF-8"?>
<classpath>
	<classpathentry kind="src" path="src"/>
	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.8"/>
	<classpathentry kind="con" path="org.eclipse.fx.ide.jdt.core.JAVAFX_CONTAINER"/>
	<classpathentry kind="lib" path="libs/antlr-2.7.7.jar"/>
	<classpathentry kind="lib" path="libs/classmate-1.3.0.jar"/>
	<classpathentry kind="lib" path="libs/dom4j-1.6.1.jar"/>
	<classpathentry kind="lib" path="libs/hibernate-commons-annotations-5.0.1.Final.jar"/>
	<classpathentry kind="lib" path="libs/hibernate-core-5.2.10.Final.jar"/>
	<classpathentry kind="lib" path="libs/hibernate-jpa-2.1-api-1.0.0.Final.jar"/>
	<classpathentry kind="lib" path="libs/jandex-2.0.3.Final.jar"/>
	<classpathentry kind="lib" path="libs/javassist-3.20.0-GA.jar"/>
	<classpathentry kind="lib" path="libs/jboss-logging-3.3.0.Final.jar"/>
	<classpathentry kind="lib" path="libs/jboss-transaction-api_1.2_spec-1.0.1.Final.jar"/>
	<classpathentry kind="lib" path="libs/jfoenix.jar"/>
	<classpathentry kind="lib" path="libs/mysql-connector-java-5.1.35-bin.jar"/>
	<classpathentry kind="output" path="bin"/>
</classpath>
```

.project

```xml
<?xml version="1.0" encoding="UTF-8"?>
<projectDescription>
	<name>Chamados</name>
	<comment></comment>
	<projects>
	</projects>
	<buildSpec>
		<buildCommand>
			<name>org.eclipse.jdt.core.javabuilder</name>
			<arguments>
			</arguments>
		</buildCommand>
		<buildCommand>
			<name>org.eclipse.xtext.ui.shared.xtextBuilder</name>
			<arguments>
			</arguments>
		</buildCommand>
	</buildSpec>
	<natures>
		<nature>org.eclipse.xtext.ui.shared.xtextNature</nature>
		<nature>org.eclipse.jdt.core.javanature</nature>
	</natures>
</projectDescription>
```

or download: 
https://drive.google.com/drive/folders/19pbs9CCl4IgQfZcmKWLi6Cp6oABlQneg?usp=sharing
