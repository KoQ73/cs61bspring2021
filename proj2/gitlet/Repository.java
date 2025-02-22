package gitlet;

import java.io.File;
import java.sql.Ref;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *  
 *  @author Kan
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The commits directory */
    public static final File COMMITS = join(GITLET_DIR, "commits");
    /** The head commit */
    public static final File HEAD = join(GITLET_DIR, "HEAD");
    /** THe branch name */
    public static final File BRANCH = join(GITLET_DIR, "BRANCH");
    /** Directory to save bolbs */
    public static final File OBJECTS = join(GITLET_DIR, "objects");
    /** The master branch */
    public static final File MASTER = join(GITLET_DIR, "MASTER");

    public static boolean isGit() {
        return GITLET_DIR.exists();
    }

    public static boolean fileExists(String s) {
        File f = Utils.join(CWD, s);
        return f.exists();
    }

    public static void init() {
        // Make a .gitlet folder if it does not exists
        GITLET_DIR.mkdir();
        // Make a commit folder
        COMMITS.mkdir();
        // Make an object folder
        OBJECTS.mkdir();
        // Make a staging area folder
        StagingArea.init();
        // Make an initial commit
        Commit initial = new Commit();
        // Save the commit
        initial.saveCommit();
        // Make a refs folder and keep tracks of head and the branches
        ReferenceArea.init(initial);
    }

    /** Adds a file named s to the staging area
     *  Doesn't add if the file is identical to the version in the current commit
     * */
    public static void add(String toAdd) {
        Commit head = ReferenceArea.getHeadCommit();
        StagingArea.add(toAdd, head);
    }

    public static void commit(String message) {
        // Get the parent commit
        Commit parent = ReferenceArea.getHeadCommit();
        // Check if there are files to commit in staging area or maybe there are files to keep untracked of
        if (StagingArea.isEmpty()) {
            System.out.println("No changes added to the commit");
            return;
        }
        // Create new commit
        Commit new_commit = new Commit(message, parent.hash());
        // Save new commit
        new_commit.saveCommit();
        // Clear staging area
        StagingArea.clearStagingArea();
        // Move Head pointer and maybe the branch
        ReferenceArea.saveHead(new_commit);
        ReferenceArea.saveMaster(new_commit);
    }

    public static void rm(String s) {
        // Remove from staging area
        StagingArea.rm(s, ReferenceArea.getHeadCommit());
    }

    /** Starting at the current head commit, display information about each commit backwards along
     *  the commit tree until it reaches initial commit, following the first parent commit links,
     *  ignoring any second parents found in merge commits.
     * */
    public static void log() {
        // Get the head commit
        Commit traveller = ReferenceArea.getHeadCommit();
        // Loop till the initial commit
        while(!traveller.isRoot()) {
            // print the current commit
            System.out.println(traveller);
            // Move forward the traveller to parent commit
            String parent = traveller.getParent();
            File f = Utils.join(COMMITS, parent);
            traveller = Utils.readObject(f, Commit.class);
        }
        // Print the initial commit or root
        System.out.print(traveller);
    }

    public static void global_log() {
        List<String> allCommits = Utils.plainFilenamesIn(COMMITS);
        for (String fileName: allCommits) {
            File f = Utils.join(COMMITS, fileName);
            Commit c = Utils.readObject(f, Commit.class);
            System.out.println(c);
        }
    }

    public static void find(String s) {
        List<String> allCommits = Utils.plainFilenamesIn(COMMITS);
        for (String fileName: allCommits) {
            File f = Utils.join(COMMITS, fileName);
            Commit c = Utils.readObject(f, Commit.class);
            // Check if the messages are the same
            if (s.equals(c.getMessage())) {
                System.out.println(c.hash());
            }
        }
    }

    /** Displays what branches currently exist, and marks the current branch with a *.
      * Also displays what files have been staged for addition or removal. */
    public static void status() {
        StringBuilder sb = new StringBuilder();
        // Print the branches and the current branch that the head is on
        String currentBranch = ReferenceArea.checkWhichBranchHeadIsOn();
        sb.append("=== Branches ===\n");
        for (String branch: ReferenceArea.listBranches()) {
            if (branch.equals(currentBranch)) {
                sb.append("*");
            }
            sb.append(branch).append("\n");
        }
        sb.append("\n");
        // Print the staged files
        sb.append("=== Staged Files ===\n");
        for (String stagedFile: StagingArea.getStagedFiles()) {
            sb.append(stagedFile).append("\n");
        }
        sb.append("\n");
        // Print files to be removed
        sb.append("=== Removed Files ===\n");
        for (String removedFile: StagingArea.getFilesToBeRemoved()) {
            sb.append(removedFile).append("\n");
        }
        sb.append("\n");
        // Print Modifications Not staged for commit
        sb.append("=== Modifications Not Staged For Commit ===\n\n");
        // Print Untracked File
        sb.append("=== Untracked Files ===\n\n");
    }

    /** Takes the version of the file as it exists in the head commit
     * and puts it in the working directory, overwriting the version of the file that’s already there if there is one.
     * The new version of the file is not staged. */
    public static void checkout(String fileName) {
        checkout(Utils.readContentsAsString(ReferenceArea.HEAD), fileName);
    }

    /** Takes the version of the file as it exists in the commit with the given id,
     * and puts it in the working directory, overwriting the version of the file that’s already there if there is one.
     * The new version of the file is not staged.
     */
    public static void checkout(String commitId, String fileName) {
        // Check if commit exists
        File check = Utils.join(COMMITS, commitId);
        if (!check.exists()) {
            System.out.println("No commit with that id exists.");
            return;
        }
        File commitFile = Utils.join(COMMITS, commitId);
        Commit commit = Utils.readObject(commitFile, Commit.class);
        if (!commit.fileExists(fileName)) {
            System.out.println("File does not exist in that commit.");
            return;
        }
        // Get the file in head commit if it exists
        File file = commit.getFileInCommit(fileName);
        // Overwrite the file in CWD
        File fileInCWD = Utils.join(CWD, fileName);
        String contents = Utils.readContentsAsString(file);
        Utils.writeContents(fileInCWD, contents);
    }

    /** Takes all files in the commit at the head of the given branch,
     * and puts them in the working directory, overwriting the versions of the files that are already there if they exist.
     * Also, at the end of this command, the given branch will now be considered the current branch (HEAD).
     * Any files that are tracked in the current branch but are not present in the checked-out branch are deleted.
     * The staging area is cleared, unless the checked-out branch is the current branch */
    public static void checkoutBranch(String branchName) {
        // Get the head commit of the given branch
        Commit branchHead = ReferenceArea.getBranchHeadCommit(branchName);
        // Overwrite file in CWD with the files in branchHead
        for (String key: branchHead.getFileMappings().keySet()) {
            // Get the blob file
            File blob = branchHead.getFileInCommit(key);
            // Overwrite the file in CWD
            File f = Utils.join(CWD, key);
            String contents = Utils.readContentsAsString(blob);
            Utils.writeContents(f, contents);
        }
        // Find files that are tracked in the current branch but not present in checked-out branch
        for (String stagedFiles : StagingArea.getStagedFiles()) {
            if (!branchHead.fileExists(stagedFiles)) {
                // Delete the file in CWD
                File toDelete = Utils.join(CWD, stagedFiles);
                toDelete.delete();
            }
        }
        /** If a working file is untracked in the current branch and would be overwritten by the checkout,
         * print There is an untracked file in the way; delete it, or add and commit it first. and exit;
         * perform this check before doing anything else. Do not change the CWD.
         * */

    }

    /* TODO: fill in the rest of this class. */
    public static void main(String[] args) {
        System.out.println(CWD);

    }
}
