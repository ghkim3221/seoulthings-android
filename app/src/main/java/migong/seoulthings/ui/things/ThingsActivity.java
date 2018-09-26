package migong.seoulthings.ui.things;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import migong.seoulthings.R;
import migong.seoulthings.data.Category;
import migong.seoulthings.ui.search.SearchActivity;
import migong.seoulthings.ui.search.SearchView;
import migong.seoulthings.ui.things.adapter.ThingsRecyclerAdapter;
import org.apache.commons.lang3.StringUtils;

public class ThingsActivity extends AppCompatActivity implements ThingsView {

  private RecyclerView mRecycler;
  private ThingsRecyclerAdapter mRecyclerAdapter;

  private String mCategory;
  private ThingsViewModel mThingsViewModel;
  private ThingsPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.things_activity);

    final Intent intent = getIntent();
    final Bundle args = intent.getExtras();
    if (args == null) {
      finish();
      return;
    }

    mCategory = args.getString(KEY_CATEGORY);
    if (StringUtils.isEmpty(mCategory)) {
      finish();
      return;
    }

    mThingsViewModel = ViewModelProviders.of(this).get(ThingsViewModel.class);
    mThingsViewModel.setCategory(mCategory);

    setupAppBar();
    setupRecycler();

    mPresenter = new ThingsPresenter(this);
    mPresenter.onCreate(savedInstanceState);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mPresenter.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mPresenter.onPause();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mPresenter.onDestroy();
  }

  @Override
  public void startSearchActivity() {
    Intent intent = new Intent(this, SearchActivity.class);

    Bundle args = new Bundle();
    args.putString(SearchView.KEY_SCOPE, SearchView.SCOPE_THINGS);
    args.putString(SearchView.KEY_CATEGORY, mCategory);
    intent.putExtras(args);

    startActivity(intent);
  }

  private void setupAppBar() {
    ImageButton backButton = findViewById(R.id.things_back_button);
    backButton.setOnClickListener(v -> onBackPressed());

    TextView titleText = findViewById(R.id.things_title);
    titleText.setText(getTitleResId());

    ImageButton searchButton = findViewById(R.id.things_search_button);
    searchButton.setOnClickListener(v -> mPresenter.onSearchButtonClicked());
  }

  private void setupRecycler() {
    mRecycler = findViewById(R.id.things_recycler);
    mRecycler.setHasFixedSize(true);
    mRecycler.setLayoutManager(new LinearLayoutManager(this));
    mRecycler.addItemDecoration(new DividerItemDecoration(mRecycler.getContext(),
        LinearLayout.VERTICAL));

    mRecyclerAdapter = new ThingsRecyclerAdapter();
    mRecycler.setAdapter(mRecyclerAdapter);
    mThingsViewModel.getThings().observe(this, mRecyclerAdapter::submitList);
  }

  @StringRes
  private int getTitleResId() {
    switch (mCategory) {
      case Category.BICYCLE:
        return R.string.bicycle;
      case Category.MEDICAL_DEVICE:
        return R.string.medical_device;
      case Category.POWER_BANK:
        return R.string.power_bank;
      case Category.SUIT:
        return R.string.suit;
      case Category.TOOL:
        return R.string.tool;
      case Category.TOY:
        return R.string.toy;
      default:
        return R.string.msg_loading;
    }
  }
}
