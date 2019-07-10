# Kotlin Tutorial

## Command Line

### Setup

- Download [Kotlin v1.3.40](https://github.com/JetBrains/kotlin/releases/tag/v1.3.40) (or) [Kotlin v1.3.31](https://github.com/JetBrains/kotlin/releases/tag/v1.3.31)
- Unzip on to a standard Location.
- Set `kotlinc_2/bin/` to standart PATH variable

  ```bash
  $ vi ~/.bash_profile
  #ADDED FOR KOTLIN
  export PATH="~/Softwares/kotlinc_2/bin/":$PATH

  #Re-Execute Bash Profile
  $ source ~/.bash_profile

  #Verify Kotlin version
  $ kotlinc -version
  #info: kotlinc-jvm 1.3.40 (JRE 12.0.1+12)
  $
  ```

## Kotlin Proposals

- [Kotlin Evolution and Enhancement Process - KEEP](https://github.com/Kotlin/KEEP)
