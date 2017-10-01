import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.util.Iterator;

public class LogCommdTest {

    private static final int LOG_NUMBER = 10;

    public static void main(String[] args) {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        File gitFile = new File(".git");
        try (Repository repository = builder.setGitDir(gitFile)
                .readEnvironment()
                .findGitDir()
                .build()) {
            Git git = new Git(repository);
            ObjectId head = repository.resolve(Constants.HEAD);
            Iterable<RevCommit> revCommits = git.log().add(head).setMaxCount(LOG_NUMBER).call();
            Iterator<RevCommit> iterator = revCommits.iterator();
            for (Iterator<RevCommit> it = iterator; it.hasNext(); ) {
                RevCommit revCommit = it.next();
                String meg = revCommit.getFullMessage();
                System.out.println(meg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
