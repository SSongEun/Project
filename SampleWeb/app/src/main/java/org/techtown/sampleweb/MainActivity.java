package org.techtown.sampleweb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    /**
     * 웹사이트 로딩을 위한 버튼
     */
    private Button loadButton;
    /**
     * 핸들러 객체
     */
    private Handler handler = new Handler();

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// 1) 웹뷰 객체 참조
        webView = (WebView) findViewById(R.id.webView);
// 2) 웹뷰에 WebSettings 설정 정보
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
// 3) 웹뷰에 클라이언트 객체 지정
        webView.setWebChromeClient(new WebBrowserClient());
// 4) 웹뷰에 자바스크립트 인터페이스 객체 지정
        webView.addJavascriptInterface(new JavaScriptMethods(), "sample");
        // 5) 웹뷰에 assets 폴더에 있는 메인 페이지 로딩
        webView.loadUrl("file:///android_asset/www/sample.html");
        final EditText urlInput = (EditText) findViewById(R.id.urlInput);
// 버튼 이벤트 처리
        loadButton = (Button) findViewById(R.id.loadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
// 6) 사용자가 직접 입력한 URL의 페이지 로딩
                webView.loadUrl(urlInput.getText().toString());
            }
        });
    }
}
/** * 자바스크립트 함수를 호출하기 위한 클래스 정의 */
public class JavaScriptMethods {
    JavaScriptMethods() {}
    // 7) 애플리케이션에서 정의한 메소드로 웹페이지에서 호출할 대상
    @android.webkit.JavascriptInterface
    public void clickOnFace() {
// 8) 핸들러로 처리
        handler.post(new Runnable() {
            public void run() {
// 9) 애플리케이션 화면의 버튼의 텍스트 변경
                loadButton.setText("클릭후열기");
// 10) 웹페이지의 자바스크립트 함수 호출
                webView.loadUrl("javascript:changeFace()");
            }
        });
    }
}
// 11) 웹브라우저 클라이언트 클래스 정의
final class WebBrowserClient extends WebChromeClient {
    public Boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Log.d("MainActivity", message);
        result.confirm();
        return true;
    }
}

