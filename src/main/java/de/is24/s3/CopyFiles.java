package de.is24.s3;

import static de.is24.s3.CopyJob.initJob;

class CopyFiles {
    public static void main(final String[] args) {
        final CopyJob copyJob = args.length == 2 ? initJob(args[0], args[1]) : initJob();

        copyJob.findFilenames().forEach(copyJob::copyFile);
    }
}
