/*
 * Copyright 2018, Robert Dyer, Mohd Arafat
 *                 and Bowling Green State University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package boa.graphs.trees;

import java.util.HashSet;
import java.util.Set;

import boa.types.Ast.Statement;
import boa.types.Ast.Expression;
import boa.graphs.cfg.CFGNode;

/**
 * @author marafat
 */

public class TreeNode implements Comparable<TreeNode> {

    private TreeNode parent;
    private int id;
    private Statement stmt;
    private Expression expr;

    private Set<TreeNode> children = new HashSet<TreeNode>();

    public TreeNode(CFGNode node) {
        this.id = node.getId();
        this.stmt = node.getStmt();
        this.expr = node.getExpr();
    }

    public TreeNode(int id) {
        this.id = id;
    }

    //Setters
    public void setParent(final TreeNode parent) {
        this.parent = parent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStmt(final Statement stmt) {
        this.stmt = stmt;
    }

    public void setExpr(final Expression expr) {
        this.expr = expr;
    }

    public void addChild(final TreeNode node) {
        children.add(node);
    }

    //Getters
    public TreeNode getParent() {
        return parent;
    }

    public int getId() {
        return id;
    }

    public Statement getStmt() {
        return stmt;
    }

    public Expression getExpr() {
        return expr;
    }

    public Set<TreeNode> getChildren() {
        return children;
    }

    @Override
    public int compareTo(final TreeNode node) {
        return node.id - this.id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TreeNode treeNode = (TreeNode) o;

        return id == treeNode.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}