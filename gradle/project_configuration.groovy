class ProjectConfiguration {

    final MODULE_BUILD_FILE_NAME_KTS = "build.gradle.kts"
    final MODULE_BUILD_FILE_NAME = "build.gradle"

    final sourceRoot = new File(projectRoot, "sources")
    File projectRoot

    ProjectConfiguration(File projectRoot) {
        this.projectRoot = projectRoot
    }

    void apply(Closure include) {
        includeAllModules(include)
    }

    void includeAllModules(Closure include) {
        final foundModuleDirs = new LinkedList([sourceRoot])
        while (!foundModuleDirs.isEmpty()) {
            final moduleDir = foundModuleDirs.poll()
            if (isModuleRoot(moduleDir)) {
                include(":${moduleDir.name}", moduleDir)
            } else {
                foundModuleDirs.addAll(moduleDir.listFiles() ?: new File[0])
            }
        }
    }

    boolean isModuleRoot(File file) {
        return new File(file, MODULE_BUILD_FILE_NAME_KTS).exists() || new File(file, MODULE_BUILD_FILE_NAME).exists()
    }
}

new ProjectConfiguration(rootProject.projectDir).apply { moduleName, moduleDir ->
    include(moduleName)
    project(moduleName).projectDir = moduleDir
}
