package com.cdm.network.interf;

public interface ResultHandler<S, F> {
    public void onSucceeded(S successObj);

	public void onFailed(F failObj);
}
