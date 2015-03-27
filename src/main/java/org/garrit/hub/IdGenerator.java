package org.garrit.hub;

import org.garrit.common.messages.Submission;

/**
 * Generates IDs for submissions.
 *
 * @author Samuel Coleman <samuel@seenet.ca>
 * @since 1.0.0
 */
public interface IdGenerator
{
    /**
     * @return a unique ID for a submission
     */
    public int getId(Submission submission);
}