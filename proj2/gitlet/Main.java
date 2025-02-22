package gitlet;

import java.io.File;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Kan San
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // if args is empty, exit the program
        if (args.length <= 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        String firstArg = args[0];

        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                // If there is a .gitlet folder already
                if (Repository.isGit()) {
                    System.out.println("A Gitlet version-control system already exists in the current directory.");
                    return;
                }
                // Make the repo a gitlet
                Repository.init();
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                // Check it is a gitlet folder
                if (!Repository.isGit()) {
                    System.out.println("Not a gitlet directory.");
                    return;
                }
                // Validate number of operands
                if (!validNumberOfArguments(args.length, 1)) {
                    System.out.println("Incorrect operands.");
                    return;
                }
                // Validate file
                if (!Repository.fileExists(args[1])) {
                    System.out.println("File does not exist.");
                    return;
                }
                // Add to staging area
                Repository.add(args[1]);
                break;
            case "commit":
                // Check it is a gitlet folder
                if (!Repository.isGit()) {
                    System.out.println("Not a gitlet directory.");
                    return;
                }
                // Validate number of operands
                if (!validNumberOfArguments(args.length, 1)) {
                    System.out.println("Incorrect operands.");
                    return;
                }
                // Validate argument
                if (!validArgument(args[1])) {
                    System.out.println("Please enter a commit message.");
                }
                // Commit
                Repository.commit(args[1]);
                break;
            // TODO: FILL THE REST IN
            case "rm":
                // Check it is a gitlet folder
                if (!Repository.isGit()) {
                    System.out.println("Not a gitlet directory.");
                    return;
                }
                // Validate number of operands
                if (!validNumberOfArguments(args.length, 1)) {
                    System.out.println("Incorrect operands.");
                    return;
                }
                // rm the file
                Repository.rm(args[1]);
                break;
            case "log":
                if (!Repository.isGit()) {
                    System.out.println("Not a gitlet directory.");
                    return;
                }
                // Validate number of operands
                if (!validNumberOfArguments(args.length, 0)) {
                    System.out.println("Incorrect operands.");
                }
                // log the head commit
                Repository.log();
                break;
            case "global-log":
                if (!Repository.isGit()) {
                    System.out.println("Not a gitlet directory.");
                }
                // Validate number of operands
                if (!validNumberOfArguments(args.length, 0)) {
                    System.out.println("Incorrect operands.");
                }
                // log all commits unordered
                Repository.global_log();
                break;
            case "find":
                if (!Repository.isGit()) {
                    System.out.println("Not a gitlet directory.");
                }
                // Validate number of operands
                if (!validNumberOfArguments(args.length, 1)) {
                    System.out.println("Incorrect operands.");
                }
                // Prints out the ids of all the commits that have the given commit message
                Repository.find(args[1]);
                break;
            case "status":
                if (!Repository.isGit()) {
                    System.out.println("Not a gitlet directory.");
                }
                // Validate number of operands
                if (!validNumberOfArguments(args.length, 0)) {
                    System.out.println("Incorrect operands.");
                }
                // Status
                Repository.status();
                break;
            case "checkout":
                // Three possible use cases
                if (!Repository.isGit()) {
                    System.out.println("Not a gitlet directory.");
                }
                // Can be two, three and one
                if (validNumberOfArguments(args.length, 1)) {
                   String branchName = args[1];
                   if (ReferenceArea.branchExists(branchName)) {
                       System.out.println("No such branch exists.");
                   }
                   else if (branchName.equals(ReferenceArea.checkWhichBranchHeadIsOn())) {
                       System.out.println("No need to checkout the current branch.");
                   }
                   else {
                       Repository.checkoutBranch(branchName);
                   }
                }
                else if (validNumberOfArguments(args.length, 2)) {
                    String fileName = args[2];
                    Repository.checkout(fileName);
                }
                else if (validNumberOfArguments(args.length, 3)) {
                    String commitId = args[1];
                    String fileName = args[3];
                    Repository.checkout(commitId, fileName);
                }
                else {
                    System.out.println("Incorrect operands.");
                }
                break;
            case "branch":
                if (!Repository.isGit()) {
                    System.out.println("Not a gitlet directory.");
                }
                // Validate number of operands
                if (!validNumberOfArguments(args.length, 1)) {
                    System.out.println("Incorrect operands.");
                }
                // Check if branch already exists
                if (ReferenceArea.branchExists(args[1])) {
                    System.out.println("A branch with that name already exists.");
                }
                else {
                    ReferenceArea.addBranch(args[1]);
                }
                break;
            default:
                System.out.println("No command with that name exists.");
        }
    }

    /** check if the number of arguments are valid.
     *  args: the number of actual arguments
     *  allowed: the number of allowed arguments
     * */
    public static boolean validNumberOfArguments(int args, int allowed) {
        args -= 1;
        return args == allowed;
    }

    public static boolean validArgument(String tocheck) {
        if (tocheck.length() < 1) {
            return false;
        }
        return true;
    }

}
