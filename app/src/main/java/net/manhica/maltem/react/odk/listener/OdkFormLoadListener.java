package net.manhica.maltem.react.odk.listener;

import android.net.Uri;

public interface OdkFormLoadListener {

	public void onOdkFormLoadSuccess(Uri contentUri);
	
	public void onOdkFormLoadFailure();

}
