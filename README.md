# RadioButtonDemo

A Java Swing assignment demonstrating five mutually exclusive pet radio buttons. The default selection is **Pig**. Selecting a pet updates the image area and displays a confirmation dialog. Missing images are handled gracefully with an `Image not found: <filename>` message.

## Project files

```text
RadioButtonDemo/
├── RadioButtonDemo.java
├── README.md
└── images/                 # optional pet images
    ├── bird.jpg
    ├── cat.jpg
    ├── dog.jpg
    ├── rabbit.jpg
    └── pig.jpg
```

## Setup and run

1. Install a JDK (Java 8 or later) and confirm that `java -version` and `javac -version` work in your terminal.
2. Create a project folder, for example `RadioButtonDemo`, and save `RadioButtonDemo.java` and this README in it.
3. Optionally create an `images` folder in the project folder. Add your pet images with these exact filenames: `bird.jpg`, `cat.jpg`, `dog.jpg`, `rabbit.jpg`, and `pig.jpg`. The program runs without these files and displays a text fallback instead. JPEG images are expected.
4. From the project folder, compile and run:

   ```sh
   javac RadioButtonDemo.java
   java RadioButtonDemo
   ```

   Or compile into a separate output directory:

   ```sh
   mkdir -p out
   javac -d out RadioButtonDemo.java
   java -cp out RadioButtonDemo
   ```

   Run from the project folder so the program can find `images/`. The window is titled **RadioButtonDemo**.

## GitHub submission

Create a new GitHub repository, then from the project folder run (replace the URL with your repository URL):

```sh
git init
git add RadioButtonDemo.java README.md images
git commit -m "Add RadioButtonDemo Swing assignment"
git branch -M main
git remote add origin https://github.com/USERNAME/REPOSITORY.git
git push -u origin main
```

Include only images you are permitted to redistribute. If you do not have images, omit `images` from the `git add` command; the fallback behavior is part of the assignment.
