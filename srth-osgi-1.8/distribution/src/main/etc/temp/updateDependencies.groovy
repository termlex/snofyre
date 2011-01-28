/**
 * Crown Copyright (C) 2008 - 2011
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// groovys script for updating dependencies html file
def depHtmlFile = new File('${project.build.outputDirectory}/settings/dependencies.html')
def depTextFile = new File('${project.build.outputDirectory}/settings/dependencies.txt')
def scanner = new Scanner(depTextFile)
// open unordered list tag
depHtmlFile.append('<ul>')
while(scanner.hasNextLine())
{
  def line = scanner.nextLine().trim()
  if((line.indexOf('The following files') < 0) && (line.length() > 0)
      && (line.indexOf('uk.nhs.cfh.dsp') < 0))
  {
    depHtmlFile.append('<li>'+line);
  }
}
// close unordered list tag
depHtmlFile.append('</ul>')
depHtmlFile.append('</body></html>')

// move file to staging directory
depHtmlFile.renameTo(new File('${staging.dir}', depHtmlFile.getName()));
