package gitlet;

import java.io.File;
import java.util.List;

import static gitlet.Utils.join;
import static gitlet.Utils.readContentsAsString;

public class ReferenceArea {
    /**
     * TODO: add instance variables here.
     *
     * The class is to keep track of the branches and head of a gitlet project
     *
     *
     */

    /** Get the head commit */
    /** Add a branch */
    /** Keep track of the branch */
    /** Maybe saving sha1 of the commit */

    /** The directory to store references */
    public static final File REFS = join(Repository.GITLET_DIR, "REFS");
    /** The directory to store branches */
    public static final File BRANCHES = join(REFS, "BRANCHES");
    /** The master branch */
    public static final File MASTER = join(BRANCHES, "master");
    /** The head ref */
    public static final File HEAD = join(REFS, "head");

    /** Make a directory for references like head and branches */
    public static void init(Commit c) {
        REFS.mkdir();
        BRANCHES.mkdir();
        // Head points to the initial commit
        saveHead(c);
        // Head is also on master branch
        saveMaster(c);
    }

    public static void saveMaster(Commit c) {
        Utils.writeContents(MASTER, c.hash());
    }

    public static void saveHead(Commit c) {
        Utils.writeContents(HEAD, c.hash());
    }

    public static Commit getHeadCommit() {
        String h = Utils.readContentsAsString(HEAD);
        File commitFile = Utils.join(Repository.COMMITS, h);
        Commit head = Utils.readObject(commitFile, Commit.class);
        return head;
    }

    public static Commit getBranchHeadCommit(String branchName) {
        if (!branchExists(branchName)) {
            return null;
        }
        File branchHead = Utils.join(BRANCHES, branchName);
        String branchHash = Utils.readContentsAsString(branchHead);
        File branchCommitFile = Utils.join(Repository.COMMITS, branchHash);
        Commit branchCommitHead = Utils.readObject(branchCommitFile, Commit.class);
        return branchCommitHead;
    }

    public static boolean branchExists(String s) {
        File f = Utils.join(BRANCHES, s);
        if (f.exists()) {
            return true;
        }
        return false;
    }

    public static void addBranch(String s) {
        // Make a new file
        File newBranch = Utils.join(BRANCHES, s);
        // Copy the sha1 of the head
        String sha1 = Utils.readContentsAsString(HEAD);
        Utils.writeContents(newBranch, sha1);
    }

    public static List<String> listBranches() {
        return Utils.plainFilenamesIn(BRANCHES);
    }

    public static String checkWhichBranchHeadIsOn() {
        String head_sha1 = readContentsAsString(HEAD);
        for (String branch: listBranches()) {
            String branch_sha1 = readContentsAsString(Utils.join(BRANCHES, branch));
            if (head_sha1.equals(branch_sha1)) {
                return branch;
            }
        }
        return null;
    }

}
