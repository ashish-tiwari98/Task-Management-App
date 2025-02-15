# 🚀 Running Jenkins Locally Using `.war` on Windows

## **📌 Prerequisites**
Before starting, ensure the following are installed:
- ✅ **Java JDK 11 or later**  
- ✅ **Jenkins `.war` file** (download from [Jenkins official site](https://www.jenkins.io/download/))

---

## **1️⃣ Verify Java Installation**
1. Open **Command Prompt (cmd)** and run:
   ```sh
   java -version
   ```
2. If Java is **not installed**, download and install the **JDK** from:
   - [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
   - [OpenJDK](https://adoptium.net/)

---

## **2️⃣ Download Jenkins `.war` File**
1. Go to [Jenkins official site](https://www.jenkins.io/download/).
2. Click on **"Generic Java package (.war)"** and download it.
3. Move the `.war` file to a convenient location (e.g., `C:\Jenkins`).

---

## **3️⃣ Start Jenkins Using the `.war` File**
1. Open **Command Prompt (cmd)**.
2. Navigate to the folder where the `.war` file is located:
   ```sh
   cd C:\Jenkins
   ```
3. Start Jenkins by running:
   ```sh
   java -jar jenkins.war
   ```
4. Wait until you see:
   ```
   Jenkins is fully up and running on http://localhost:8080/
   ```

---

## **4️⃣ Access Jenkins in Browser**
1. Open your browser.
2. Go to:
   ```
   http://localhost:8080/
   ```

---

## **5️⃣ Unlock Jenkins (First-Time Setup)**
1. Jenkins will ask for an **Administrator password**.
2. Find it in the Command Prompt:
   ```
   Please use the following password to proceed: XXXXXX
   ```
3. Copy the password and paste it into the Jenkins **Unlock Jenkins** page.
4. Click **Continue**.

---

## **6️⃣ Install Suggested Plugins**
1. Choose **Install suggested plugins**.
2. Wait for Jenkins to install plugins.

---

## **7️⃣ Create Admin User**
1. Enter **Username**, **Password**, **Full Name**, and **Email**.
2. Click **Save and Continue**.

---

## **8️⃣ Jenkins is Ready!**
1. Click **Start using Jenkins**.
2. You will be redirected to the Jenkins dashboard.

---

## **🛠️ Managing Jenkins**
### ✅ **Stop Jenkins**
- Press `CTRL + C` in the command prompt.
- Alternatively, close the command prompt window.

### ✅ **Restart Jenkins**
- Run the command again:
  ```sh
  java -jar jenkins.war
  ```

### ✅ **Change Jenkins Port (Optional)**
- If **port 8080** is in use, run:
  ```sh
  java -jar jenkins.war --httpPort=9090
  ```
- Access Jenkins at:
  ```
  http://localhost:9090/
  ```

---

## **🎯 Conclusion**
You have successfully set up and run Jenkins **locally** using the `.war` file. 🎉

For advanced configuration, check the **Jenkins documentation**: [https://www.jenkins.io/doc/](https://www.jenkins.io/doc/).

