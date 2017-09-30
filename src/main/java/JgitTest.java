import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.util.Iterator;

public class JgitTest {

    public static void main(String[] args) {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        File gitFile = new File(".git");
        try (Repository repository = builder.setGitDir(gitFile)
                .readEnvironment()
                .findGitDir()
                .build()) {
            Git git = new Git(repository);
            LogCommand logCommand = git.log();
            Iterable<RevCommit> revCommits = logCommand.call();
            Iterator<RevCommit> iterator = revCommits.iterator();
            while (iterator.hasNext()) {
                RevCommit revCommit = iterator.next();
                String meg = revCommit.getFullMessage();
                System.out.println(meg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
