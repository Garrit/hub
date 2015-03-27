package org.garrit.hub;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

import org.garrit.common.messages.Submission;

/**
 * Linearly generates IDs. Stores the last ID generated in a file to ensure
 * uniqueness across multiple program runs.
 *
 * @author Samuel Coleman <samuel@seenet.ca>
 * @since 1.0.0
 */
@Slf4j
public class FilePersistingIdGenerator implements IdGenerator
{
    private final Path idTracker;
    private int lastId;

    public FilePersistingIdGenerator(Path idTracker)
    {
        this.idTracker = idTracker;

        try
        {
            Scanner scanner = new Scanner(idTracker);
            this.lastId = scanner.nextInt();
        }
        catch (IOException e)
        {
            log.warn("Couldn't read last ID from file. Next ID generated will be 1.");
            this.lastId = 0;
        }
    }

    @Override
    public synchronized int getId(Submission submission)
    {
        this.lastId++;

        try
        {
            new FileWriter(this.idTracker.toFile()).write(String.format("%d\n", this.lastId));
        }
        catch (IOException e)
        {
            log.warn("Couldn't write new ID to file. Repeat IDs may result on subsequent program runs!");
        }

        return this.lastId;
    }

}
