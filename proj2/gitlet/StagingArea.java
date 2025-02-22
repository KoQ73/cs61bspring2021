package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static gitlet.Utils.join;
import static gitlet.Utils.writeContents;

public class StagingArea implements Serializable {

    /** The staging area directory */
    public static final File STAGING_DIR = join(Repository.GITLET_DIR, "index");
    /** List<string> object that keep tracks of the name of files to be removed </string>*/
    public static final File TO_REMOVE_FILES = join(Repository.GITLET_DIR, "rmFiles");

    /** Make a staging Area for files staged for addition and removal */
    public static void init() {
        STAGING_DIR.mkdir();
        TO_REMOVE_FILES.mkdir();
    }

    public static void add(String s, Commit head) {
        File fileToAdd = Utils.join(Repository.CWD, s);
        File stagedFile = Utils.join(STAGING_DIR, s);
        String contentsOfFile = Utils.readContentsAsString(fileToAdd);
        // Check the file exists in previous commit
        if (head.fileExists(s)) {
            // Don't add if the contents are the same and remove it if it exists in staging area
            if (head.checkContentsWithTheFile(fileToAdd)) {
                // Remove the file in staging area if it exists
                File toRemove = Utils.join(STAGING_DIR, s);
                if (toRemove.exists()) {
                    toRemove.delete();
                }
            }
            else {
                // Add it to the staging area
                Utils.writeContents(stagedFile, contentsOfFile);
            }
        }
        else {
            // If the file doesn't exist in staging area and also in previous commt
            if (!stagedFile.exists()) {
                writeContents(stagedFile, contentsOfFile);
                return;
            }
            // If the file exists in staging area but the content are different
            if (!sameContents(stagedFile, fileToAdd)) {
                writeContents(stagedFile, contentsOfFile);
            }
        }
    }

    private static boolean sameContents(File f1, File f2) {
        String s1 = Utils.readContentsAsString(f1);
        String s2 = Utils.readContentsAsString(f2);
        return s1.equals(s2);
    }

    public static boolean isEmpty() {
        List<String> staged_files = getStagedFiles();
        List<String> filesToBeRemoved = getFilesToBeRemoved();
        if (staged_files.isEmpty() && filesToBeRemoved.isEmpty()) {
            System.out.println("No changes added to the commit.");
            return true;
        }
        return false;
    }

    public static List<String> getFilesToBeRemoved() {
        return Utils.plainFilenamesIn(TO_REMOVE_FILES);
    }

    public static List<String> getStagedFiles() {
        List<String> stagedFiles = Utils.plainFilenamesIn(STAGING_DIR);
        return stagedFiles;
    }

    public static void clearStagingArea() {
        // delete the files in directory
        List<String> files = Utils.plainFilenamesIn(STAGING_DIR);
        for (String s: files) {
            File f = Utils.join(STAGING_DIR, s);
            f.delete();
        }
        // clear the files in removed files
        List<String> r = getFilesToBeRemoved();
        for (String s: r) {
            File f = Utils.join(TO_REMOVE_FILES, s);
            f.delete();
        }
    }

    public static boolean stagedFileExists(String s) {
        File stagedFile = Utils.join(STAGING_DIR, s);
        return stagedFile.exists();
    }

    public static void rm(String s, Commit head) {
        // If the file is in staging area, remove from the staging area
        File stagedFile = Utils.join(STAGING_DIR, s);
        if (stagedFileExists(s)) {
            stagedFile.delete();
            return;
        }
        // If the file is tracked in the current commit, stage it for removal
        // and remove the file from working directory if the user has not already done so
        if (head.fileExists(s)) {
            // Stage it for removal
            File emptyFile = Utils.join(TO_REMOVE_FILES, s);
            Utils.writeContents(emptyFile, "");
            // Remove the file from CWD
            File toRemove = Utils.join(Repository.CWD, s);
            Utils.restrictedDelete(toRemove);
            return;
        }
        System.out.println("No reason to remove the file.");
    }

    public static void isUntracked(String fileName) {

    }

}
