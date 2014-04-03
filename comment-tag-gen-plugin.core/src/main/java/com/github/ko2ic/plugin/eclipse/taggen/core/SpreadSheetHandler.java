/*******************************************************************************
 * Copyright (c) 2014
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Kouji Ishii - initial implementation
 *******************************************************************************/
package com.github.ko2ic.plugin.eclipse.taggen.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.ClassElements;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.element.GeneratingCodeSeedBase;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.ItemsTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.domain.model.tag.RootTag;
import com.github.ko2ic.plugin.eclipse.taggen.common.service.parse.TagHandlerBase;
import com.github.ko2ic.plugin.eclipse.taggen.core.domain.valueobject.SelectObject;
import com.github.ko2ic.plugin.eclipse.taggen.core.domain.valueobject.TemplateCode;
import com.github.ko2ic.plugin.eclipse.taggen.core.exceptions.AppException;
import com.github.ko2ic.plugin.eclipse.taggen.core.exceptions.AppFileNotFoundException;
import com.github.ko2ic.plugin.eclipse.taggen.core.persistense.PreferenceDao;
import com.github.ko2ic.plugin.eclipse.taggen.core.service.CustomCodeFinder;
import com.github.ko2ic.plugin.eclipse.taggen.core.service.JavaCodeWriter;
import com.github.ko2ic.plugin.eclipse.taggen.core.service.SpreadSheetToBeanConverter;
import com.github.ko2ic.plugin.eclipse.taggen.core.service.parse.TagExtracter;
import com.github.ko2ic.plugin.eclipse.taggen.core.service.parse.TagExtracter.TagClosureCreator;
import com.github.ko2ic.plugin.eclipse.taggen.core.ui.components.dialog.AsyncMessageDialog;

/**
 * Executed by right click on spreadsheet.<br/>
 * @author ko2ic
 */
public class SpreadSheetHandler extends AbstractHandler {

    private final ILog log = Activator.getDefault().getLog();

    /**
     * {@inheritDoc}
     */
    @Override
    // @SneakyThrows
    public Object execute(final ExecutionEvent event) throws ExecutionException {

        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        final Shell shell = window.getShell();

        WorkspaceJob job = new WorkspaceJob("Generating") {
            @Override
            public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
                monitor.beginTask("Generating", 100);

                final CustomCodeFinder finder = new CustomCodeFinder();

                try {
                    final String filePath = finder.findTemplateFilePath();
                    final GeneratingCodeSeedBase codeSeed = finder.findCodeSeed();
                    final ItemsTag itemsTag = finder.findItemsTag();
                    final RootTag rootTag = finder.findRootTag();
                    final TagHandlerBase tagHandler = finder.findTagHandler(rootTag);
                    monitor.worked(4);

                    TemplateCode code = null;
                    try (InputStream is = new FileInputStream(filePath)) {
                        code = new TemplateCode(is);
                    } catch (FileNotFoundException e) {
                        throw new AppFileNotFoundException(filePath);
                    }
                    monitor.worked(2);

                    PreferenceDao preferenceDao = new PreferenceDao();

                    SelectObject selection = new SelectObject(event, preferenceDao.findOutputFolder());
                    IFile spreadsheetFile = selection.getFile();

                    SpreadSheetToBeanConverter converter = new SpreadSheetToBeanConverter(new SubProgressMonitor(monitor, 10), spreadsheetFile, codeSeed);

                    Map<String, ? extends ClassElements> map = converter.getResult();

                    TagClosureCreator<ItemsTag> itemsTagClosureCreator = new TagClosureCreator<ItemsTag>() {
                        @Override
                        protected ItemsTag instance() {
                            return itemsTag;
                        }
                    };

                    TagExtracter tagExtracter = new TagExtracter(code.getTagLines(), itemsTagClosureCreator, tagHandler);
                    monitor.worked(2);

                    JavaCodeWriter writer = new JavaCodeWriter(new SubProgressMonitor(monitor, 82), selection, code, tagExtracter);
                    writer.write(map);
                } catch (AppException e) {
                    AsyncMessageDialog.openWarning(shell, "Comment Tag Gen", e.getErrorMessage());
                } catch (OperationCanceledException e) {
                    AsyncMessageDialog.openInformation(shell, "Comment Tag Gen", "Canceled !");
                    return Status.CANCEL_STATUS;
                } catch (Exception e) {
                    log.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
                    String str = e.getMessage() != null ? e.getMessage() : "";
                    AsyncMessageDialog.openError(shell, "Comment Tag Gen", "Important Error Occured." + str);
                } finally {
                    monitor.done();
                }
                return Status.OK_STATUS;
            }
        };
        job.setUser(true);
        job.schedule();

        return null;
    }
}
