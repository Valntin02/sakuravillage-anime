package com.sakuravillage.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import com.sakuravillage.R;
import com.sakuravillage.network.NetworkManager;

import java.util.Locale;

/**
 * 个人中心Fragment
 */
public class ProfileFragment extends BaseFragment {

    private NetworkManager networkManager;
    private TextView profileInitialView;
    private TextView profileNameView;
    private TextView profileSubtitleView;
    private TextView profileBadgeView;
    private TextView accountStatusView;
    private TextView accountIdView;
    private TextView syncStatusView;
    private com.google.android.material.button.MaterialButton primaryActionButton;
    private com.google.android.material.button.MaterialButton secondaryActionButton;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initView(View view) {
        networkManager = NetworkManager.getInstance(requireContext());

        profileInitialView = view.findViewById(R.id.tv_profile_initial);
        profileNameView = view.findViewById(R.id.tv_profile_name);
        profileSubtitleView = view.findViewById(R.id.tv_profile_subtitle);
        profileBadgeView = view.findViewById(R.id.tv_profile_badge);
        accountStatusView = view.findViewById(R.id.tv_account_status_value);
        accountIdView = view.findViewById(R.id.tv_account_id_value);
        syncStatusView = view.findViewById(R.id.tv_sync_status_value);
        primaryActionButton = view.findViewById(R.id.btn_primary_action);
        secondaryActionButton = view.findViewById(R.id.btn_secondary_action);

        setupClickListeners(view);
    }

    @Override
    protected void initData() {
        renderProfile();
    }

    @Override
    public void onResume() {
        super.onResume();
        renderProfile();
    }

    private void setupClickListeners(View view) {
        primaryActionButton.setOnClickListener(v -> {
            if (networkManager.isLoggedIn()) {
                showToast(R.string.profile_toast_manage_account);
            } else {
                showToast(R.string.profile_toast_login);
            }
        });

        secondaryActionButton.setOnClickListener(v -> {
            if (networkManager.isLoggedIn()) {
                showLogoutDialog();
            } else {
                showToast(R.string.profile_toast_benefits);
            }
        });

        view.findViewById(R.id.card_history).setOnClickListener(v -> showToast(R.string.profile_toast_history));
        view.findViewById(R.id.card_star).setOnClickListener(v -> showToast(R.string.profile_toast_star));
        view.findViewById(R.id.card_download).setOnClickListener(v -> showToast(R.string.profile_toast_download));
        view.findViewById(R.id.card_account).setOnClickListener(v -> {
            if (networkManager.isLoggedIn()) {
                showToast(R.string.profile_toast_manage_account);
            } else {
                showToast(R.string.profile_toast_login);
            }
        });
        view.findViewById(R.id.row_theme).setOnClickListener(v -> showToast(R.string.profile_toast_theme));
        view.findViewById(R.id.row_help).setOnClickListener(v -> showToast(R.string.profile_toast_help));
    }

    private void renderProfile() {
        if (!isAdded() || networkManager == null) {
            return;
        }

        boolean loggedIn = networkManager.isLoggedIn();
        String userName = networkManager.getUserName();
        int userId = networkManager.getUserId();

        if (TextUtils.isEmpty(userName)) {
            userName = getString(R.string.profile_default_user_name);
        }

        profileInitialView.setText(getProfileInitial(userName));
        profileNameView.setText(loggedIn
                ? getString(R.string.profile_greeting_user, userName)
                : getString(R.string.profile_greeting_guest));
        profileSubtitleView.setText(loggedIn
                ? R.string.profile_subtitle_logged_in
                : R.string.profile_subtitle_guest);
        profileBadgeView.setText(loggedIn
                ? R.string.profile_badge_logged_in
                : R.string.profile_badge_guest);
        primaryActionButton.setText(loggedIn
                ? R.string.profile_primary_action_logged_in
                : R.string.profile_primary_action_guest);
        secondaryActionButton.setText(loggedIn
                ? R.string.profile_secondary_action_logged_in
                : R.string.profile_secondary_action_guest);
        accountStatusView.setText(loggedIn
                ? R.string.profile_status_logged_in
                : R.string.profile_status_guest);
        accountIdView.setText(loggedIn && userId > 0
                ? "#" + userId
                : getString(R.string.profile_value_pending));
        syncStatusView.setText(loggedIn
                ? R.string.profile_sync_on
                : R.string.profile_sync_off);
    }

    private String getProfileInitial(String userName) {
        if (TextUtils.isEmpty(userName)) {
            return getString(R.string.profile_default_initial);
        }

        String trimmedName = userName.trim();
        if (trimmedName.isEmpty()) {
            return getString(R.string.profile_default_initial);
        }

        return trimmedName.substring(0, 1).toUpperCase(Locale.getDefault());
    }

    private void showLogoutDialog() {
        if (!isAdded()) {
            return;
        }

        new AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
                .setTitle(R.string.profile_logout_dialog_title)
                .setMessage(R.string.profile_logout_dialog_message)
                .setNegativeButton(R.string.profile_logout_cancel, null)
                .setPositiveButton(R.string.profile_logout_confirm, (dialog, which) -> {
                    networkManager.logout();
                    renderProfile();
                    showToast(R.string.profile_logout_success);
                })
                .show();
    }

    private void showToast(@StringRes int messageRes) {
        if (!isAdded()) {
            return;
        }
        Toast.makeText(requireContext(), messageRes, Toast.LENGTH_SHORT).show();
    }
}
