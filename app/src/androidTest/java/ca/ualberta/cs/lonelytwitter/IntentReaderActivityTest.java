package ca.ualberta.cs.lonelytwitter;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.TextView;

/**
 * Created by sajediba on 2/8/16.
 */
public class IntentReaderActivityTest extends ActivityInstrumentationTestCase2{

    public IntentReaderActivityTest() {
        super(IntentReaderActivity.class);
    }

    //
    //
    public void testSendText(){
        Intent intent = new Intent();
        intent.putExtra(IntentReaderActivity.TEXT_TO_TRANSFORM_KEY,"message 1");

        setActivityIntent(intent);
        IntentReaderActivity ira = (IntentReaderActivity) getActivity();

        assertEquals("Test Send text",ira.getText(),"message 1");
    }
    //
    public void testDisplayText(){
        Intent intent = new Intent();
        intent.putExtra(IntentReaderActivity.TEXT_TO_TRANSFORM_KEY,"message 2");

        setActivityIntent(intent);
        IntentReaderActivity ira = (IntentReaderActivity) getActivity();
        TextView textView = (TextView) ira.findViewById(R.id.intentText);
        assertEquals("Test Display Text",textView.getText().toString(),"message 2");

    }
    //
    public void testDoubleText(){
        Intent intent = new Intent();
        intent.putExtra(IntentReaderActivity.TEXT_TO_TRANSFORM_KEY,"message 2");
        intent.putExtra(IntentReaderActivity.MODE_OF_TRANSFORM_KEY,IntentReaderActivity.DOUBLE);

        setActivityIntent(intent);
        IntentReaderActivity ira = (IntentReaderActivity) getActivity();
        TextView textView = (TextView) ira.findViewById(R.id.intentText);
        assertEquals("Test Display Text", textView.getText().toString(), "message 2message 2");
    }

    //TODO: Add your code here ...
//-------------------------------------------------------------------------------
    public void testReverseText(){
        Intent intent = new Intent();
        intent.putExtra(IntentReaderActivity.TEXT_TO_TRANSFORM_KEY,"message 2");
        intent.putExtra(IntentReaderActivity.MODE_OF_TRANSFORM_KEY,IntentReaderActivity.REVERSE);

        setActivityIntent(intent);
        IntentReaderActivity ira = (IntentReaderActivity) getActivity();
        TextView textView = (TextView) ira.findViewById(R.id.intentText);
        assertEquals("Test Display Text", textView.getText().toString(), "2 egassem");
    }

    public void testDefaultText(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        IntentReaderActivity ira = (IntentReaderActivity) getActivity();
        TextView textView = (TextView) ira.findViewById(R.id.intentText);
        assertEquals("Test Display Text", textView.getText().toString(), "default text");
    }

    public void testTextViewVisible(){
        Intent intent = new Intent();

        setActivityIntent(intent);
        IntentReaderActivity ira = (IntentReaderActivity) getActivity();

        ViewAsserts.assertOnScreen(ira.getWindow().getDecorView(),((TextView)ira.findViewById(R.id.intentText)));
    }
//-------------------------------------------------------------------------------
}
