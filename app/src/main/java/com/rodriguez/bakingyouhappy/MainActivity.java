package com.rodriguez.bakingyouhappy;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private WebView webView; // Declare WebView for displaying HTML content
    private Button checkoutButton; // Declare Checkout button
    private static final String TAG = "MainActivity"; // Tag for logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the WebView
        webView = findViewById(R.id.webview);
        checkoutButton = findViewById(R.id.checkout_button);

        if (webView != null) {
            setupWebView();
            loadLoginPage();
        } else {
            Log.e(TAG, "WebView not found in layout.");
        }

        if (checkoutButton != null) {
            setupCheckoutButton();
        } else {
            Log.e(TAG, "Checkout button not found in layout.");
        }
    }

    private void setupWebView() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                // Show the checkout button only on the checkout page
                if (url.contains("checkout.html")) {
                    checkoutButton.setVisibility(View.VISIBLE);
                } else {
                    checkoutButton.setVisibility(View.GONE);
                }
            }
        });
        webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript if needed
    }

    private void loadLoginPage() {
        webView.loadUrl("file:///android_asset/login.html");
    }

    private void setupCheckoutButton() {
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showModal(); // Show the modal when "Check Out" is pressed
            }
        });
    }

    // Method to show the modal dialog
    private void showModal() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.modal_layout); // Ensure this matches your modal layout file

        // Optional: Set the dialog's background to transparent
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        // Set up the "Home" button in the modal
        Button homeButton = dialog.findViewById(R.id.modal_button);
        if (homeButton != null) {
            setupHomeButton(homeButton, dialog);
        } else {
            Log.e(TAG, "Home button not found in modal layout.");
        }

        dialog.show();
    }

    private void setupHomeButton(Button homeButton, Dialog dialog) {
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Close the modal
                loadHomePage(); // Load the home page
            }
        });
    }

    private void loadHomePage() {
        if (webView != null) {
            webView.loadUrl("file:///android_asset/home.html"); // Load the home page
        } else {
            Log.e(TAG, "WebView not found when loading home page.");
        }
    }
}
