package org.ardonplay.aois.KarnaughTableData;

import java.util.List;

public class DataSet {

    public DataSet() {

        this.tables = List.of(new DataTable(new int[][]{
                {1, 1, 1, 1}, {1, 1, 1, 1}}), new DataTable(new int[][]{
                {1, 1, 1, 1}, {0, 0, 0, 0}}), new DataTable(new int[][]{
                {0, 0, 0, 0}, {1, 1, 1, 1}}), new DataTable(new int[][]{
                {1, 1, 0, 0}, {1, 1, 0, 0}}), new DataTable(new int[][]{
                {0, 1, 1, 0}, {0, 1, 1, 0}}), new DataTable(new int[][]{
                {0, 0, 1, 1}, {0, 0, 1, 1}}), new DataTable(new int[][]{
                {1, 0, 0, 1}, {1, 0, 0, 1}}), new DataTable(new int[][]{
                {1, 1, 0, 0}, {0, 0, 0, 0}}), new DataTable(new int[][]{
                {0, 1, 1, 0}, {0, 0, 0, 0}}), new DataTable(new int[][]{
                {0, 0, 1, 1}, {0, 0, 0, 0}}), new DataTable(new int[][]{
                {1, 0, 0, 1}, {0, 0, 0, 0}}), new DataTable(new int[][]{
                {0, 0, 0, 0}, {1, 1, 0, 0}}), new DataTable(new int[][]{
                {0, 0, 0, 0}, {0, 1, 1, 0}}), new DataTable(new int[][]{
                {0, 0, 0, 0}, {0, 0, 1, 1}}), new DataTable(new int[][]{
                {0, 0, 0, 0}, {1, 0, 0, 1}}), new DataTable(new int[][]{
                {1, 0, 0, 0}, {1, 0, 0, 0}}), new DataTable(new int[][]{
                {0, 1, 0, 0}, {0, 1, 0, 0}}), new DataTable(new int[][]{
                {0, 0, 1, 0}, {0, 0, 1, 0}}), new DataTable(new int[][]{
                {0, 0, 0, 1}, {0, 0, 0, 1}}), new DataTable(new int[][]{
                {1, 0, 0, 0}, {0, 0, 0, 0}}), new DataTable(new int[][]{
                {0, 1, 0, 0}, {0, 0, 0, 0}}), new DataTable(new int[][]{
                {0, 0, 1, 0}, {0, 0, 0, 0}}), new DataTable(new int[][]{
                {0, 0, 0, 1}, {0, 0, 0, 0}}), new DataTable(new int[][]{
                {0, 0, 0, 0}, {1, 0, 0, 0}}), new DataTable(new int[][]{
                {0, 0, 0, 0}, {0, 1, 0, 0}}), new DataTable(new int[][]{
                {0, 0, 0, 0}, {0, 0, 1, 0}}), new DataTable(new int[][]{
                {0, 0, 0, 0}, {0, 0, 0, 1}}));
    }

    @Deprecated
    public void reverse(){
        for(DataTable table: tables){
            table.reverse();
        }
    }
    private final List<DataTable> tables;

    public List<DataTable> getTables() {

        return tables;
    }
}
