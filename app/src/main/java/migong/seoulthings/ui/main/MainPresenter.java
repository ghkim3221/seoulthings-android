package migong.seoulthings.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import migong.seoulthings.R;
import migong.seoulthings.ui.Presenter;
import migong.seoulthings.ui.category.CategoryFragment;
import migong.seoulthings.ui.community.CommunityFragment;
import migong.seoulthings.ui.notification.NotificationFragment;
import migong.seoulthings.ui.profile.ProfileFragment;

public class MainPresenter implements Presenter {

  private static final String TAG = MainPresenter.class.getSimpleName();

  @NonNull
  private final MainView mView;

  public MainPresenter(@NonNull MainView view) {
    this.mView = view;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");

    mView.addFragment(new CategoryFragment());
  }

  @Override
  public void onResume() {
    Log.d(TAG, "onResume() called");
  }

  @Override
  public void onPause() {
    Log.d(TAG, "onPause() called");
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, "onDestroy() called");
  }

  public boolean onBottomNavigationItemSelected(@NonNull MenuItem item) {
    Log.d(TAG, "onBottomNavigationItemSelected() called with: item = [" + item + "]");

    switch (item.getItemId()) {
      case R.id.bottom_navigation_category:
        mView.replaceFragment(new CategoryFragment());
        return true;
      case R.id.bottom_navigation_community:
        mView.replaceFragment(new CommunityFragment());
        return true;
      case R.id.bottom_navigation_notification:
        mView.replaceFragment(new NotificationFragment());
        return true;
      case R.id.bottom_navigation_profile:
        mView.replaceFragment(new ProfileFragment());
        return true;
    }

    return false;
  }
}
