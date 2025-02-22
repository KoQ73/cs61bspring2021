package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;
import java.util.List;

import static gitlet.Utils.join;
import static gitlet.Utils.writeContents;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */
    /** The head commit */
    public static final File HEAD = join(Repository.GITLET_DIR, "HEAD");

    /** The message of this Commit. */
    private String message;
    private Date timestamp;
    private String parent;
    private String mergeParent;
    /** Mapp file name to its blob object's UID */
    private HashMap<String, String> fileMappings;

    /** Initial commit initialization*/
    public Commit() {
        this.message = "initial commit";
        this.timestamp = new Date(0);
        this.parent = null;
        this.mergeParent = null;
        this.fileMappings = new HashMap<>();
    }

    public String hash() {
        return Utils.sha1(Utils.serialize(message), Utils.serialize(timestamp), Utils.serialize(parent), Utils.serialize(mergeParent));
    }

    public Commit(String message, String parent) {
        this.message = message;
        this.timestamp = new Date();
        this.parent = parent;
        // Copy from parent
        File parent_file = Utils.join(Repository.COMMITS, parent);
        Commit c = Utils.readObject(parent_file, Commit.class);
        this.fileMappings = new HashMap<>(c.fileMappings);
        // Update the parent commit with new data
        this.update();
    }

    public HashMap<String, String> getFileMappings() {
        return fileMappings;
    }

    /** Update contents in commit from the staging area */
    private void update() {
        List<String> staged_files = StagingArea.getStagedFiles();
        // Update commit
        for (String s : staged_files) {
            File f = Utils.join(StagingArea.STAGING_DIR, s);
            this.fileMappings.put(s, Utils.sha1(Utils.readContentsAsString(f)));
        }
        // Remove files that are staged for removal
        List<String> toRemoveFiles = StagingArea.getFilesToBeRemoved();
        for (String s: toRemoveFiles) {
            this.fileMappings.remove(s);
        }
    }

    public boolean checkContentsWithTheFile(File f) {
        String fileName = f.getName();
        String content_hash = this.fileMappings.get(fileName);
        String file_hash = Utils.sha1(Utils.readContentsAsString(f));
        if (content_hash == null) {
            return false;
        }
        return content_hash.equals(file_hash);
    }

    public boolean fileExists(String s) {
        String k = this.fileMappings.get(s);
        if (k == null) {
            return false;
        }
        return true;
    }

    public void saveCommit() {
        // Save the new files in objects folder
        for (String key: this.fileMappings.keySet()) {
            // Make the file name of the object a sha1
            File blob = Utils.join(Repository.OBJECTS, this.fileMappings.get(key));
            // If the contents are different, save it
            if (!blob.exists()) {
                File staged_file = Utils.join(StagingArea.STAGING_DIR, key);
                String contents = Utils.readContentsAsString(staged_file);
                writeContents(blob, contents);
            }
        }
        // Save the new commit in commits folder
        File f = join(Repository.COMMITS, this.hash());
        Utils.writeObject(f, this);
    }

    public String getParent() {
        return this.parent;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean isMergeCommit() {
        return this.mergeParent != null;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");
        StringBuilder sb = new StringBuilder();

        sb.append("===\n");
        sb.append("commit ").append(this.hash()).append("\n");

        // If the commit has a second parent
        if (this.isMergeCommit()) {
            sb.append("Merge: ")
                    .append(this.parent.substring(0, 7)).append(" ")
                    .append(this.mergeParent.substring(0, 7)).append("\n");
        }

        // Date
        sb.append("Date: ").append(formatter.format(this.timestamp)).append("\n");
        sb.append(this.message).append("\n");

        return sb.toString();
    }

    public File getFileInCommit(String fileName) {
        if (this.fileExists(fileName)) {
            String sha1 = this.fileMappings.get(fileName);
            File f = Utils.join(Repository.OBJECTS, sha1);
            return f;
        }
        return null;
    }

    /* TODO: fill in the rest of this class. */
}
