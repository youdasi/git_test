import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import java.io.File;
import java.io.IOException;

public class RevWalkTest {
    private static final int LOG_NUMBER = 10;
    public static void main(String[] args) {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        File gitFile = new File(".git");
        try (Repository repository = builder.setGitDir(gitFile)
                .readEnvironment()
                .findGitDir()
                .build()) {
            RevWalk revWalk = new RevWalk(repository);
            revWalk.markStart(revWalk.parseCommit(repository.resolve(Constants.HEAD)));
            revWalk.sort(RevSort.REVERSE);
            revWalk.setRevFilter(new RevFilter() {
                @Override
                public boolean include(RevWalk revWalk, RevCommit revCommit)
                        throws StopWalkException,
                        IOException {
                    return revCommit.getAuthorIdent().getName().equals("陈东");
                }

                @Override
                public RevFilter clone() {
                    return null;
                }
            });
            for (RevCommit revCommit : revWalk) {
                String msg = revCommit.getFullMessage();
                System.out.println(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
