


/*
prerequisite
1. Create GCP Bucket.
2. Try to run the script for creation of bucket and docker images.

Implementing the following.
1. Read from Google Drive sample test data.  (or from Private Bucket)
2. Perform Count words or basic transformation
3. Write into personally Google Drive

Deploy in DataFlow, Etc.
Use Kubernetes to deply KStreams
Use CSC deployment
or Kafka Connector deployment .
Docker

* */

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.*;
import org.apache.beam.sdk.values.PCollection;

public class readAndWriteGS {

    public interface SimpleOptions extends PipelineOptions {

        /**
         * By default, this example reads from a public dataset containing the text of King Lear. Set
         * this option to choose a different input file or glob.
         */
        @Description("Path of the file to read from")
        //@Default.String("gs://gcp_beam_input/wordCountSample.txt")
        @Default.String("gs://apache-beam-samples/shakespeare/kinglear.txt")
        String getInputFile();

        void setInputFile(String value);

        /** Set this required option to specify where to write the output. */
        @Description("Path of the file to write to")
        @Validation.Required
        //@Default.String("gs://gcp_beam_output/")
        @Default.String("/OutPut/")
        String getOutput();

        void setOutput(String value);
    }



    public static void main(String[] args) {

        SimpleOptions options =
                PipelineOptionsFactory.fromArgs(args).withValidation().as(SimpleOptions.class);

        Pipeline p = Pipeline.create(options);
        p.apply("ReadLines", TextIO.read().from(options.getInputFile()))
                .apply("WriteCounts", TextIO.write().to(options.getOutput()));

        //PCollection<String> pCollection = p.apply("ReadLines", TextIO.read().from(options.getInputFile()));
        //pCollection.apply()


    }
}
