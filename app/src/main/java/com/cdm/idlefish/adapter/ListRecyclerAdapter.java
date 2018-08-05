package com.cdm.idlefish.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ListRecyclerAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private final ArrayList<T> items = new ArrayList<>();

    @Override
    public int getItemCount() {
        return items.size();
    }

    public T get(int position) {
        return this.items.get(position);
    }

    public void add(int position, T item) {
        this.items.add(position, item);
        notifyItemInserted(position);
    }

    public void add(T... items) {
        this.items.addAll(Arrays.asList(items));
        notifyDataSetChanged();
    }

    public void add(List<T> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        this.items.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(T item) {
        this.items.remove(item);
        notifyDataSetChanged();
    }

    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public ArrayList<T> getDataList(){
        return items;
    }
}
